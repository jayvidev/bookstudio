package com.bookstudio.copy.application.dto.response;

import com.bookstudio.copy.domain.model.type.CopyCondition;
import com.bookstudio.copy.domain.model.type.CopyStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "book", "shelf", "barcode", "status", "condition" })
public record CopyDetailResponse(
    Long id,
    String code,

    @JsonIgnore Long bookId,
    @JsonIgnore String bookIsbn,
    @JsonIgnore String bookCoverUrl,
    @JsonIgnore String bookTitle,

    @JsonIgnore Long shelfId,
    @JsonIgnore String shelfCode,
    @JsonIgnore String shelfFloor,
    @JsonIgnore String shelfDescription,

    String barcode,
    CopyStatus status,
    CopyCondition condition
) {

    @JsonGetter("book")
    public Book getBook() {
        return new Book(bookId, bookIsbn, bookCoverUrl, bookTitle);
    }

    @JsonGetter("shelf")
    public Shelf getShelf() {
        return new Shelf(shelfId, shelfCode, shelfFloor, shelfDescription);
    }

    public record Book(
        Long id,
        String isbn,
        String coverUrl,
        String title
    ) {}

    public record Shelf(
        Long id,
        String code,
        String floor,
        String description
    ) {}
}
