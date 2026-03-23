package com.bookstudio.book.application.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.bookstudio.shared.type.Status;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "title", "isbn", "language", "edition", "pages", "description", "coverUrl", "publisher",
        "category", "releaseDate", "status", "authors", "genres" })
public record BookDetailResponse(
    Long id,
    String title,
    String isbn,

    @JsonIgnore Long languageId,
    @JsonIgnore String languageCode,
    @JsonIgnore String languageName,

    String edition,
    Integer pages,
    String description,
    String coverUrl,

    @JsonIgnore Long publisherId,
    @JsonIgnore String publisherName,

    @JsonIgnore Long categoryId,
    @JsonIgnore String categoryName,

    LocalDate releaseDate,
    Status status,

    List<AuthorItem> authors,
    List<GenreItem> genres
) {

    public BookDetailResponse withAuthorsAndGenres(List<AuthorItem> authors, List<GenreItem> genres) {
        return new BookDetailResponse(
            id, title, isbn, languageId, languageCode, languageName, edition, pages, description, coverUrl,
            publisherId, publisherName, categoryId, categoryName, releaseDate, status, authors, genres
        );
    }

    @JsonGetter("language")
    public Language getLanguage() {
        return new Language(languageId, languageCode, languageName);
    }

    @JsonGetter("publisher")
    public Publisher getPublisher() {
        return new Publisher(publisherId, publisherName);
    }

    @JsonGetter("category")
    public Category getCategory() {
        return new Category(categoryId, categoryName);
    }

    public record Language(
        Long id,
        String code,
        String name
    ) {}

    public record Publisher(
        Long id,
        String name
    ) {}

    public record Category(
        Long id,
        String name
    ) {}

    public record AuthorItem(
        Long id,
        String name
    ) {}

    public record GenreItem(
        Long id,
        String name
    ) {}
}
