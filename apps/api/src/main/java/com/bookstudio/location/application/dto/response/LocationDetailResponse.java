package com.bookstudio.location.application.dto.response;

import java.util.List;

public record LocationDetailResponse(
    Long id,
    String name,
    String description,
    List<ShelfItem> shelves
) {

    public LocationDetailResponse withShelves(List<ShelfItem> shelves) {
        return new LocationDetailResponse(
            id,
            name,
            description,
            shelves
        );
    }

    public record ShelfItem(
        Long id,
        String code,
        String floor,
        String description
    ) {}
}
