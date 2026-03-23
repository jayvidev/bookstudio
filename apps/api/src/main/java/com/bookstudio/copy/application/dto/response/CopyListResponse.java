package com.bookstudio.copy.application.dto.response;

import com.bookstudio.copy.domain.model.type.CopyCondition;
import com.bookstudio.copy.domain.model.type.CopyStatus;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "code", "book", "shelf", "location", "status", "condition" })
public record CopyListResponse(
    Long id,
    String code,

    @JsonIgnore Long bookId,
    @JsonIgnore String bookCoverUrl,
    @JsonIgnore String bookTitle,

    @JsonIgnore String shelfCode,
    @JsonIgnore String shelfFloor,

    @JsonIgnore String locationName,

    CopyStatus status,
    CopyCondition condition
) {

    @JsonGetter("book")
    public Book getBook() {
        return new Book(bookId, bookCoverUrl, bookTitle);
    }

    @JsonGetter("shelf")
    public Shelf getShelf() {
        return new Shelf(shelfCode, shelfFloor);
    }

    @JsonGetter("location")
    public Location getLocation() {
        return new Location(locationName);
    }

    public record Book(
        Long id,
        String coverUrl,
        String title
    ) {}

    public record Shelf(
        String code,
        String floor
    ) {}

    public record Location(
        String name
    ) {}
}
