package com.bookstudio.inventory.copy.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.inventory.copy.application.dto.response.CopyDetailResponse;
import com.bookstudio.inventory.copy.application.dto.response.CopyListResponse;
import com.bookstudio.inventory.copy.domain.model.Copy;
import com.bookstudio.shared.response.OptionResponse;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    String LIST_SELECT = """
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
        JOIN Book b ON b.id = c.bookId
        JOIN Shelf s ON s.id = c.shelfId
        JOIN s.location l
        """;

    @Query(LIST_SELECT + "ORDER BY c.id DESC")
    List<CopyListResponse> findList();

    @Query(LIST_SELECT + "WHERE c.id = :id")
    Optional<CopyListResponse> findListItemById(Long id);

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
        JOIN Book b ON b.id = c.bookId
        JOIN Shelf s ON s.id = c.shelfId
        WHERE c.id = :id
    """)
    Optional<CopyDetailResponse> findDetailById(Long id);

}
