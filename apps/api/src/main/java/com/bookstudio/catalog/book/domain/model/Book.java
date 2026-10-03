package com.bookstudio.catalog.book.domain.model;

import com.bookstudio.shared.type.Status;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true)
    private String isbn;

    @Column(name = "language_id", nullable = false)
    private Long languageId;

    private String edition;

    private Integer pages;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String coverUrl;

    @Column(name = "publisher_id", nullable = false)
    private Long publisherId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    @CollectionTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "author_id")
    private Set<Long> authorIds = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genre_id")
    private Set<Long> genreIds = new HashSet<>();

    public void replaceAuthors(Collection<Long> ids) {
        authorIds.clear();
        authorIds.addAll(ids);
    }

    public void replaceGenres(Collection<Long> ids) {
        genreIds.clear();
        genreIds.addAll(ids);
    }
}
