package com.bookstudio.staff.worker.infrastructure.repository;

import com.bookstudio.staff.worker.application.dto.response.WorkerDetailResponse;
import com.bookstudio.staff.worker.application.dto.response.WorkerListResponse;
import com.bookstudio.staff.worker.domain.model.Worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>, JpaSpecificationExecutor<Worker> {
    String LIST_SELECT = """
        SELECT
            w.id AS id,
            w.profilePhotoUrl AS profilePhotoUrl,
            w.username AS username,
            w.email AS email,
            CONCAT(w.firstName, ' ', w.lastName) AS fullName,

            r.id AS roleId,
            r.name AS roleName,

            w.status AS status
        FROM Worker w
        JOIN Role r ON r.id = w.roleId
        """;

    @Query(LIST_SELECT + "WHERE w.id IN :ids ")
    List<WorkerListResponse> findListByIds(Collection<Long> ids);

    @Query(LIST_SELECT + "WHERE w.id = :id")
    Optional<WorkerListResponse> findListItemById(Long id);

    @Query("""
        SELECT 
            w.id AS id,
            w.username AS username,
            w.email AS email,
            w.firstName AS firstName,
            w.lastName AS lastName,

            r.id AS roleId,
            r.name AS roleName,

            w.profilePhotoUrl AS profilePhotoUrl,
            w.status AS status
        FROM Worker w
        JOIN Role r ON r.id = w.roleId
        WHERE w.id = :id
    """)
    Optional<WorkerDetailResponse> findDetailById(Long id);

    Optional<Worker> findByUsername(String username);
    Optional<Worker> findByEmail(String email);
}
