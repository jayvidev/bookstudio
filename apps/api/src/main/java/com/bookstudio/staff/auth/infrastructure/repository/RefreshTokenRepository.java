package com.bookstudio.staff.auth.infrastructure.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.staff.auth.domain.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
        UPDATE RefreshToken t
        SET t.revokedAt = :now
        WHERE t.workerId = :workerId AND t.revokedAt IS NULL
    """)
    int revokeAllActiveByWorkerId(Long workerId, Instant now);
}
