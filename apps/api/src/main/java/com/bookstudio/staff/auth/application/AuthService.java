package com.bookstudio.staff.auth.application;

import java.time.Instant;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.exception.UnauthorizedException;
import com.bookstudio.staff.auth.application.dto.response.AuthResponse;
import com.bookstudio.staff.auth.application.dto.response.AuthUserResponse;
import com.bookstudio.staff.auth.domain.model.RefreshToken;
import com.bookstudio.staff.auth.infrastructure.repository.RefreshTokenRepository;
import com.bookstudio.staff.role.domain.model.Role;
import com.bookstudio.staff.role.infrastructure.repository.RolePermissionRepository;
import com.bookstudio.staff.role.infrastructure.repository.RoleRepository;
import com.bookstudio.staff.worker.domain.model.Worker;
import com.bookstudio.staff.worker.domain.model.type.WorkerStatus;
import com.bookstudio.staff.worker.infrastructure.repository.WorkerRepository;

/**
 * Staff authentication: password login, refresh-token rotation and logout.
 *
 * <p>Every failure answers with the same message so callers cannot tell an
 * unknown username from a wrong password or a suspended account.
 */
@Service
@Transactional
@EnableConfigurationProperties(DemoProperties.class)
public class AuthService {
    private static final String INVALID_CREDENTIALS = "Invalid username or password";
    private static final String INVALID_REFRESH_TOKEN = "Invalid or expired refresh token";

    private final WorkerRepository workerRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final DemoProperties demo;

    /** Compared against when the username does not exist, so both paths cost the same. */
    private final String dummyHash;

    public AuthService(WorkerRepository workerRepository, RoleRepository roleRepository,
            RolePermissionRepository rolePermissionRepository, RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder, TokenService tokenService, DemoProperties demo) {
        this.workerRepository = workerRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.demo = demo;
        this.dummyHash = passwordEncoder.encode("timing-equalizer");
    }

    public AuthResponse login(String username, String password) {
        Worker worker = workerRepository.findByUsername(username).orElse(null);
        String hash = worker != null ? worker.getPassword() : dummyHash;

        boolean passwordMatches = passwordEncoder.matches(password, hash);
        if (worker == null || !passwordMatches || worker.getStatus() != WorkerStatus.ACTIVO) {
            throw new UnauthorizedException(INVALID_CREDENTIALS);
        }
        return issueTokens(worker, Instant.now());
    }

    /**
     * Signs in as the configured demo account, when the demo is enabled.
     */
    public AuthResponse demoLogin() {
        if (!demo.enabled()) {
            throw new ResourceNotFoundException("Demo login is not enabled");
        }
        Worker worker = workerRepository.findByUsername(demo.username())
                .filter(w -> w.getStatus() == WorkerStatus.ACTIVO)
                .orElseThrow(() -> new IllegalStateException("Demo account '%s' is missing".formatted(demo.username())));
        return issueTokens(worker, Instant.now());
    }

    /**
     * Exchanges a refresh token for a new pair. The presented token is revoked
     * (rotation); presenting an already revoked token revokes every active token
     * of that worker, because only a stolen copy can be replayed.
     *
     * <p>{@code noRollbackFor}: the revocation must be committed even though the
     * caller gets a 401.
     */
    @Transactional(noRollbackFor = UnauthorizedException.class)
    public AuthResponse refresh(String refreshToken) {
        Instant now = Instant.now();
        RefreshToken stored = refreshTokenRepository.findByTokenHash(tokenService.hash(refreshToken))
                .orElseThrow(() -> new UnauthorizedException(INVALID_REFRESH_TOKEN));

        if (stored.isRevoked()) {
            refreshTokenRepository.revokeAllActiveByWorkerId(stored.getWorkerId(), now);
            throw new UnauthorizedException(INVALID_REFRESH_TOKEN);
        }
        if (stored.isExpired(now)) {
            throw new UnauthorizedException(INVALID_REFRESH_TOKEN);
        }

        Worker worker = workerRepository.findById(stored.getWorkerId())
                .filter(w -> w.getStatus() == WorkerStatus.ACTIVO)
                .orElseThrow(() -> new UnauthorizedException(INVALID_REFRESH_TOKEN));

        stored.revoke(now);
        return issueTokens(worker, now);
    }

    /**
     * Revokes the refresh token. Idempotent: unknown or revoked tokens are ignored.
     */
    public void logout(String refreshToken) {
        refreshTokenRepository.findByTokenHash(tokenService.hash(refreshToken))
                .ifPresent(token -> token.revoke(Instant.now()));
    }

    @Transactional(readOnly = true)
    public AuthUserResponse currentUser(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found with ID: " + workerId));
        return toUser(worker, permissionsOf(worker));
    }

    private AuthResponse issueTokens(Worker worker, Instant now) {
        List<String> permissions = permissionsOf(worker);
        AuthUserResponse user = toUser(worker, permissions);

        String accessToken = tokenService.issueAccessToken(
                worker.getId(), worker.getUsername(), user.role(), permissions, now);

        String refreshToken = tokenService.newRefreshToken();
        refreshTokenRepository.save(RefreshToken.issue(
                worker.getId(), tokenService.hash(refreshToken), now, tokenService.refreshTokenExpiry(now)));

        return new AuthResponse(accessToken, "Bearer", tokenService.accessTokenTtlSeconds(), refreshToken, user);
    }

    private List<String> permissionsOf(Worker worker) {
        return rolePermissionRepository.findPermissionCodesByRoleId(worker.getRoleId());
    }

    private AuthUserResponse toUser(Worker worker, List<String> permissions) {
        String role = roleRepository.findById(worker.getRoleId()).map(Role::getName).orElse(null);
        return new AuthUserResponse(worker.getId(), worker.getUsername(), worker.getFullName(), role, permissions);
    }
}
