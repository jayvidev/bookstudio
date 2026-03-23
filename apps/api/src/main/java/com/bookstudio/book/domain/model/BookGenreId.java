package com.bookstudio.book.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookGenreId implements Serializable {
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "genre_id")
    private Long genreId;
}
