package com.bookstudio.location.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.location.application.dto.response.LocationDetailResponse;
import com.bookstudio.location.domain.model.Location;
import com.bookstudio.location.domain.model.Shelf;
import com.bookstudio.shared.response.OptionResponse;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    @Query("""
        SELECT
            s.id AS value,
            CONCAT(s.location.name, ' - ', s.floor) AS label
        FROM Shelf s
        ORDER BY s.location.name ASC, s.floor ASC
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT
            s.id AS id,
            s.code AS code,
            s.floor AS floor,
            s.description AS description
        FROM Shelf s
        WHERE s.location.id = :id
    """)
    List<LocationDetailResponse.ShelfItem> findShelfItemsByLocationId(Long id);

    Long countByLocation(Location location);
    void deleteAllByLocation(Location location);
}
