package com.bookstudio.book.application.dto.response;

import com.bookstudio.shared.type.Status;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "isbn", "coverUrl", "title", "category", "publisher", "language", "copies", "status" })
public record BookListResponse(
    Long id,
    String isbn,
    String coverUrl,
    String title,

    @JsonIgnore Long categoryId,
    @JsonIgnore String categoryName,

    @JsonIgnore Long publisherId,
    @JsonIgnore String publisherName,

    @JsonIgnore Long languageId,
    @JsonIgnore String languageCode,
    @JsonIgnore String languageName,

    @JsonIgnore Long copiesLoaned,
    @JsonIgnore Long copiesAvailable,

    Status status
) {

    @JsonGetter("category")
    public Category getCategory() {
        return new Category(categoryId, categoryName);
    }

    @JsonGetter("publisher")
    public Publisher getPublisher() {
        return new Publisher(publisherId, publisherName);
    }

    @JsonGetter("language")
    public Language getLanguage() {
        return new Language(languageId, languageCode, languageName);
    }

    @JsonGetter("copies")
    public Copies getCopies() {
        return new Copies(copiesLoaned, copiesAvailable);
    }

    public record Category(
        Long id,
        String name
    ) {}

    public record Publisher(
        Long id,
        String name
    ) {}

    public record Language(
        Long id,
        String code,
        String name
    ) {}

    public record Copies(
        Long loaned,
        Long available
    ) {}
}
