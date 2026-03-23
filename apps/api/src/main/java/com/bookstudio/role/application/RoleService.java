package com.bookstudio.role.application;

import com.bookstudio.role.application.dto.request.CreateRoleRequest;
import com.bookstudio.role.application.dto.request.UpdateRoleRequest;
import com.bookstudio.role.application.dto.response.RoleDetailResponse;
import com.bookstudio.role.application.dto.response.RoleListResponse;
import com.bookstudio.role.domain.model.Permission;
import com.bookstudio.role.domain.model.Role;
import com.bookstudio.role.domain.model.RolePermission;
import com.bookstudio.role.domain.model.RolePermissionId;
import com.bookstudio.role.infrastructure.repository.RolePermissionRepository;
import com.bookstudio.role.infrastructure.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class RoleService {
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public List<RoleListResponse> getList() {
        return roleRepository.findList();
    }

    public RoleDetailResponse getDetailById(Long id) {
        RoleDetailResponse base = roleRepository.findDetailById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));

        return base.withPermissions(rolePermissionRepository.findPermissionItemsByRoleId(id));
    }

    @Transactional
    public RoleListResponse create(CreateRoleRequest request) {
        Role role = new Role();
        role.setName(request.name());
        role.setDescription(request.description());

        Role saved = roleRepository.save(role);

        for (Long permissionId : request.permissionIds()) {
            RolePermission relation = new RolePermission(
                    new RolePermissionId(saved.getId(), permissionId),
                    saved,
                    new Permission(permissionId));
            rolePermissionRepository.save(relation);
        }

        return toListResponse(saved);
    }

    @Transactional
    public RoleListResponse update(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));

        role.setName(request.name());
        role.setDescription(request.description());

        Role updated = roleRepository.save(role);

        rolePermissionRepository.deleteAllByRole(updated);

        for (Long permissionId : request.permissionIds()) {
            RolePermission relation = new RolePermission(
                    new RolePermissionId(updated.getId(), permissionId),
                    updated,
                    new Permission(permissionId));
            rolePermissionRepository.save(relation);
        }

        return toListResponse(updated);
    }

    private RoleListResponse toListResponse(Role role) {
        return new RoleListResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                (long) role.getRolePermissions().size());
    }
}
