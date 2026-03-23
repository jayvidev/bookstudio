package com.bookstudio.publisher.infrastructure.repository;

import com.bookstudio.publisher.application.dto.response.PublisherDetailResponse;
import com.bookstudio.publisher.application.dto.response.PublisherListResponse;
import com.bookstudio.publisher.domain.model.Publisher;
import com.bookstudio.shared.response.OptionResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query("""
        SELECT 
            p.id AS id,
            p.photoUrl AS photoUrl,
            p.name AS name,

            n.id AS nationalityId,
            n.code AS nationalityCode,
            n.name AS nationalityName,

            p.website AS website,
            p.address AS address,
            p.status AS status
        FROM Publisher p
        JOIN p.nationality n
        ORDER BY p.id DESC
    """)
    List<PublisherListResponse> findList();

    @Query("""
        SELECT
            p.id AS value,
            p.name AS label
        FROM Publisher p
        WHERE p.status = 'ACTIVO'
        ORDER BY p.name ASC
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            p.id AS id,
            p.name AS name,

            n.id AS nationalityId,
            n.code AS nationalityCode,
            n.name AS nationalityName,

            p.foundationYear AS foundationYear,
            p.website AS website,
            p.address AS address,
            p.status AS status,
            p.photoUrl AS photoUrl,
            
            NULL AS genres
        FROM Publisher p
        JOIN p.nationality n
        WHERE p.id = :id
    """)
    Optional<PublisherDetailResponse> findDetailById(Long id);
}
