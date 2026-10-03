package com.bookstudio.staff.role.infrastructure.repository;

import com.bookstudio.staff.role.application.dto.response.RoleDetailResponse;
import com.bookstudio.staff.role.domain.model.Role;
import com.bookstudio.staff.role.domain.model.RolePermission;
import com.bookstudio.staff.role.domain.model.RolePermissionId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
    @Query("""
        SELECT 
            p.id AS id,
            p.code AS code,
            p.description AS description
        FROM RolePermission rp
        JOIN rp.permission p
        WHERE rp.role.id = :id
    """)
    List<RoleDetailResponse.PermissionItem> findPermissionItemsByRoleId(Long id);

    @Query("""
        SELECT p.code
        FROM RolePermission rp
        JOIN rp.permission p
        WHERE rp.role.id = :roleId
        ORDER BY p.code
    """)
    List<String> findPermissionCodesByRoleId(Long roleId);

    void deleteAllByRole(Role role);
}
