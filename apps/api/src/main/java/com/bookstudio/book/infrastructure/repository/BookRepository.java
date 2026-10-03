package com.bookstudio.book.infrastructure.repository;

import com.bookstudio.book.application.dto.response.BookDetailResponse;
import com.bookstudio.book.application.dto.response.BookListResponse;
import com.bookstudio.book.domain.model.Book;
import com.bookstudio.shared.response.OptionResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    String LIST_SELECT = """
        SELECT 
            b.id AS id,
            b.isbn AS isbn,
            b.coverUrl AS coverUrl,
            b.title AS title,

            c.id AS categoryId,
            c.name AS categoryName,

            p.id AS publisherId,
            p.name AS publisherName,

            l.id AS languageId,
            l.code AS languageCode,
            l.name AS languageName,

            COALESCE(SUM(CASE WHEN cpy.status <> 'DISPONIBLE' THEN 1 ELSE 0 END), 0) AS copiesLoaned,
            COALESCE(SUM(CASE WHEN cpy.status = 'DISPONIBLE' THEN 1 ELSE 0 END), 0) AS copiesAvailable,

            b.status AS status
        FROM Book b
        JOIN Publisher p ON p.id = b.publisherId
        JOIN Category c ON c.id = b.categoryId
        JOIN Language l ON l.id = b.languageId
        LEFT JOIN Copy cpy ON cpy.book.id = b.id
        """;

    String LIST_GROUP_BY = """
        GROUP BY b.id, b.isbn, b.coverUrl, b.title, c.id, c.name, p.id, p.name, l.id, l.code, l.name, b.status
        """;

    @Query(LIST_SELECT + LIST_GROUP_BY + "ORDER BY b.id DESC")
    List<BookListResponse> findList();

    @Query(LIST_SELECT + "WHERE b.id = :id " + LIST_GROUP_BY)
    Optional<BookListResponse> findListItemById(Long id);

    @Query("""
        SELECT 
            b.id AS value,
            b.title AS label
        FROM Book b
        WHERE b.status = 'ACTIVO'
        ORDER BY b.title ASC
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            b.id AS id,
            b.title AS title,
            b.isbn AS isbn,

            l.id AS languageId,
            l.code AS languageCode,
            l.name AS languageName,

            b.edition AS edition,
            b.pages AS pages,
            b.description AS description,
            b.coverUrl AS coverUrl,

            p.id AS publisherId,
            p.name AS publisherName,
            
            c.id AS categoryId,
            c.name AS categoryName,
            
            b.releaseDate AS releaseDate,
            b.status AS status,
            
            NULL AS authors,
            NULL AS genres
        FROM Book b
        JOIN Publisher p ON p.id = b.publisherId
        JOIN Category c ON c.id = b.categoryId
        JOIN Language l ON l.id = b.languageId
        WHERE b.id = :id
    """)
    Optional<BookDetailResponse> findDetailById(Long id);

    @Query("""
        SELECT
            a.id AS id,
            a.name AS name
        FROM Book b
        JOIN b.authorIds authorId
        JOIN Author a ON a.id = authorId
        WHERE b.id = :id
        ORDER BY a.id
    """)
    List<BookDetailResponse.AuthorItem> findAuthorItemsByBookId(Long id);

    @Query("""
        SELECT
            g.id AS id,
            g.name AS name
        FROM Book b
        JOIN b.genreIds genreId
        JOIN Genre g ON g.id = genreId
        WHERE b.id = :id
        ORDER BY g.id
    """)
    List<BookDetailResponse.GenreItem> findGenreItemsByBookId(Long id);
}
