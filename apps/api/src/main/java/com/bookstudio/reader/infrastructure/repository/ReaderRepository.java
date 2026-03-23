package com.bookstudio.reader.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.reader.application.dto.response.ReaderDetailResponse;
import com.bookstudio.reader.application.dto.response.ReaderListResponse;
import com.bookstudio.reader.domain.model.Reader;
import com.bookstudio.shared.response.OptionResponse;

import java.util.List;
import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {    
    @Query("""
        SELECT 
            r.id AS id,
            r.code AS code,
            CONCAT(r.firstName, ' ', r.lastName) AS fullName,
            r.phone AS phone,
            r.email AS email,
            r.type AS type,
            r.status AS status
        FROM Reader r
        ORDER BY r.id DESC
    """)
    List<ReaderListResponse> findList();

    @Query("""
        SELECT 
            r.id AS value,
            CONCAT(r.firstName, ' ', r.lastName) AS label
        FROM Reader r
        WHERE r.status = 'ACTIVO'
        ORDER BY r.firstName, r.lastName
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            r.id AS id,
            r.code AS code,
            r.dni AS dni,
            r.firstName AS firstName,
            r.lastName AS lastName,
            r.address AS address,
            r.phone AS phone,
            r.email AS email,
            r.birthDate AS birthDate,
            r.gender AS gender,
            r.type AS type,
            r.status AS status
        FROM Reader r
        WHERE r.id = :id
    """)
    Optional<ReaderDetailResponse> findDetailById(Long id);

    Optional<Reader> findByDni(String dni);
    Optional<Reader> findByEmail(String email);
    Optional<Reader> findByEmailAndIdNot(String email, Long excludedId);
}
