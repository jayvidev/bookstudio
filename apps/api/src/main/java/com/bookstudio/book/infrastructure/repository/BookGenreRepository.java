package com.bookstudio.book.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.book.application.dto.response.BookDetailResponse;
import com.bookstudio.book.domain.model.Book;
import com.bookstudio.book.domain.model.BookGenre;
import com.bookstudio.book.domain.model.BookGenreId;

public interface BookGenreRepository extends JpaRepository<BookGenre, BookGenreId> {
    @Query("""
        SELECT 
            g.id AS id,
            g.name AS name
        FROM BookGenre bg
        JOIN bg.genre g
        WHERE bg.book.id = :id
    """)
    List<BookDetailResponse.GenreItem> findGenreItemsByBookId(Long id);

    void deleteAllByBook(Book book);
}
