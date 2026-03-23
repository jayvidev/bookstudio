package com.bookstudio.copy.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.copy.application.dto.response.CopyDetailResponse;
import com.bookstudio.copy.application.dto.response.CopyListResponse;
import com.bookstudio.copy.domain.model.Copy;
import com.bookstudio.location.domain.model.Location;
import com.bookstudio.shared.response.OptionResponse;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    @Query("""
        SELECT 
            c.id AS id,
            c.code AS code,

            b.id AS bookId,
            b.coverUrl AS bookCoverUrl,
            b.title AS bookTitle,

            s.code AS shelfCode,
            s.floor AS shelfFloor,

            l.name AS locationName,

            c.status AS status,
            c.condition AS condition
        FROM Copy c
        JOIN c.book b
        JOIN c.shelf s
        JOIN s.location l
        ORDER BY c.id DESC
    """)
    List<CopyListResponse> findList();

    @Query("""
        SELECT 
            c.id AS value,
            c.code AS label
        FROM Copy c
        WHERE c.status = 'DISPONIBLE'
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            c.id AS id,
            c.code AS code,

            b.id AS bookId,
            b.isbn AS bookIsbn,
            b.coverUrl AS bookCoverUrl,
            b.title AS bookTitle,

            s.id AS shelfId,
            s.code AS shelfCode,
            s.floor AS shelfFloor,
            s.description AS shelfDescription,

            c.barcode AS barcode,
            c.status AS status,
            c.condition AS condition
        FROM Copy c
        JOIN c.book b
        JOIN c.shelf s
        WHERE c.id = :id
    """)
    Optional<CopyDetailResponse> findDetailById(Long id);

    @Query("""
        SELECT COUNT(c)
        FROM Copy c
        WHERE c.shelf.location = :location
    """)
    Long countByShelfLocation(Location location);

    @Query("""
        SELECT COUNT(DISTINCT c.book.id)
        FROM Copy c
        WHERE c.shelf.location = :location
    """)
    Long countDistinctBookByShelfLocation(Location location);
}
