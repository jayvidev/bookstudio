package com.bookstudio.staff.role.application;

import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.PageProjection;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;
import com.bookstudio.shared.response.OptionResponse;
import com.bookstudio.staff.role.RoleApi;
import com.bookstudio.staff.role.application.dto.request.CreateRoleRequest;
import com.bookstudio.staff.role.application.dto.request.RoleFilter;
import com.bookstudio.staff.role.application.dto.request.UpdateRoleRequest;
import com.bookstudio.staff.role.application.dto.response.RoleDetailResponse;
import com.bookstudio.staff.role.application.dto.response.RoleListResponse;
import com.bookstudio.staff.role.domain.model.Permission;
import com.bookstudio.staff.role.domain.model.Role;
import com.bookstudio.staff.role.domain.model.RolePermission;
import com.bookstudio.staff.role.domain.model.RolePermissionId;
import com.bookstudio.staff.role.infrastructure.repository.RolePermissionRepository;
import com.bookstudio.staff.role.infrastructure.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class RoleService implements RoleApi {
    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "name");

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public void requireExists(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with ID: " + id);
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return roleRepository.findForOptions();
    }

    public PageResponse<RoleListResponse> getPage(RoleFilter filter, Pageable pageable) {
        Specification<Role> spec = Specification.allOf(
                Specs.containsIgnoreCase(filter.search(), "name"));

        return PageProjection.of(
                roleRepository.findAll(spec, SORTABLE.validate(pageable)),
                Role::getId,
                roleRepository::findListByIds,
                RoleListResponse::id);
    }

    public RoleDetailResponse getDetailById(Long id) {
        RoleDetailResponse base = roleRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));

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
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));

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
