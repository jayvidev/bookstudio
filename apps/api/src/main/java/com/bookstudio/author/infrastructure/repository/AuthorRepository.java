package com.bookstudio.author.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.author.application.dto.response.AuthorDetailResponse;
import com.bookstudio.author.application.dto.response.AuthorListResponse;
import com.bookstudio.author.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("""
        SELECT 
            a.id AS id,
            a.photoUrl AS photoUrl,
            a.name AS name,
            
            n.id AS nationalityId,
            n.code AS nationalityCode,
            n.name AS nationalityName,
            
            a.birthDate AS birthDate,
            a.status AS status
        FROM Author a
        JOIN a.nationality n
        ORDER BY a.id DESC
    """)
    List<AuthorListResponse> findList();

    @Query("""
        SELECT 
            a.id AS id,
            a.name AS name,
            
            n.id AS nationalityId,
            n.code AS nationalityCode,
            n.name AS nationalityName,
            
            a.birthDate AS birthDate,
            a.biography AS biography,
            a.status AS status,
            a.photoUrl AS photoUrl
        FROM Author a
        JOIN a.nationality n
        WHERE a.id = :id
    """)
    Optional<AuthorDetailResponse> findDetailById(Long id);
}
