package com.bookstudio.worker.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.worker.application.dto.response.WorkerDetailResponse;
import com.bookstudio.worker.application.dto.response.WorkerListResponse;
import com.bookstudio.worker.domain.model.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Query("""
        SELECT 
            w.id AS id,
            w.profilePhotoUrl AS profilePhotoUrl,
            w.username AS username,
            w.email AS email,
            CONCAT(w.firstName, ' ', w.lastName) AS fullName,

            w.role.id AS roleId,
            w.role.name AS roleName,

            w.status AS status
        FROM Worker w
        WHERE w.id <> :loggedId
        ORDER BY w.id DESC
    """)
    List<WorkerListResponse> findList(Long loggedId);

    @Query("""
        SELECT 
            w.id AS id,
            w.username AS username,
            w.email AS email,
            w.firstName AS firstName,
            w.lastName AS lastName,

            w.role.id AS roleId,
            w.role.name AS roleName,

            w.profilePhotoUrl AS profilePhotoUrl,
            w.status AS status
        FROM Worker w
        WHERE w.id = :id
    """)
    Optional<WorkerDetailResponse> findDetailById(Long id);

    Optional<Worker> findByUsername(String username);
    Optional<Worker> findByEmail(String email);
}
