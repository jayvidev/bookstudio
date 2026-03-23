package com.bookstudio.book.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.book.application.dto.response.BookDetailResponse;
import com.bookstudio.book.domain.model.Book;
import com.bookstudio.book.domain.model.BookAuthor;
import com.bookstudio.book.domain.model.BookAuthorId;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    @Query("""
        SELECT 
            a.id AS id,
            a.name AS name
        FROM BookAuthor ba
        JOIN ba.author a
        WHERE ba.book.id = :id
    """)
    List<BookDetailResponse.AuthorItem> findAuthorItemsByBookId(Long id);

    void deleteAllByBook(Book book);
}
