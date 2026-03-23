package com.bookstudio.role.infrastructure.repository;

import com.bookstudio.role.application.dto.response.RoleDetailResponse;
import com.bookstudio.role.domain.model.Role;
import com.bookstudio.role.domain.model.RolePermission;
import com.bookstudio.role.domain.model.RolePermissionId;

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

    void deleteAllByRole(Role role);
}
