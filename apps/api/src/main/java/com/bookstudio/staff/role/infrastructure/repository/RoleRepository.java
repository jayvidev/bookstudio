package com.bookstudio.staff.role.infrastructure.repository;

import com.bookstudio.shared.response.OptionResponse;
import com.bookstudio.staff.role.application.dto.response.RoleDetailResponse;
import com.bookstudio.staff.role.application.dto.response.RoleListResponse;
import com.bookstudio.staff.role.domain.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    String LIST_SELECT = """
        SELECT 
            r.id AS id,
            r.name AS name,
            r.description AS description,
            COUNT(rp.permission.id) AS permissionCount
        FROM Role r
        LEFT JOIN RolePermission rp ON rp.role = r
        """;

    String LIST_GROUP_BY = """
        GROUP BY r.id, r.name, r.description
        """;

    @Query(LIST_SELECT + "WHERE r.id IN :ids " + LIST_GROUP_BY)
    List<RoleListResponse> findListByIds(Collection<Long> ids);

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
