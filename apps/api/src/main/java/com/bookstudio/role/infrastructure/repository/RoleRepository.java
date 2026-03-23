package com.bookstudio.role.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.role.application.dto.response.RoleDetailResponse;
import com.bookstudio.role.application.dto.response.RoleListResponse;
import com.bookstudio.role.domain.model.Role;
import com.bookstudio.shared.response.OptionResponse;

public interface RoleRepository extends JpaRepository<Role, Long>{
    @Query("""
        SELECT 
            r.id AS id,
            r.name AS name,
            r.description AS description,
            COUNT(rp.permission.id) AS permissionCount
        FROM Role r
        LEFT JOIN RolePermission rp ON rp.role = r
        GROUP BY r.id, r.name, r.description
        ORDER BY r.id DESC
    """)
    List<RoleListResponse> findList();

    @Query("""
        SELECT 
            r.id AS value,
            r.name AS label
        FROM Role r
        ORDER BY r.name ASC
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            r.id AS id,
            r.name AS name,
            r.description AS description,
            NULL AS permissions
        FROM Role r
        WHERE r.id = :id
    """)
    Optional<RoleDetailResponse> findDetailById(Long id);
}
