package com.bookstudio.inventory.location.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.inventory.location.application.dto.response.LocationDetailResponse;
import com.bookstudio.inventory.location.application.dto.response.LocationListResponse;
import com.bookstudio.inventory.location.domain.model.Location;
import com.bookstudio.shared.response.OptionResponse;

public interface LocationRepository extends JpaRepository<Location, Long> {
    String LIST_SELECT = """
        SELECT
            l.id AS id,
            l.name AS name,
            l.description AS description,
            COUNT(DISTINCT s.id) AS shelfCount,
            COUNT(DISTINCT c.bookId) AS bookCount,
            COUNT(DISTINCT c.id) AS copyCount
        FROM Location l
        LEFT JOIN l.shelves s
        LEFT JOIN Copy c ON c.shelfId = s.id
        """;

    String LIST_GROUP_BY = """
        GROUP BY l.id, l.name, l.description
        """;

    @Query(LIST_SELECT + LIST_GROUP_BY + "ORDER BY l.id DESC")
    List<LocationListResponse> findList();

    @Query(LIST_SELECT + "WHERE l.id = :id " + LIST_GROUP_BY)
    Optional<LocationListResponse> findListItemById(Long id);

    @Query("""
        SELECT 
            l.id AS value,
            l.name AS label
        FROM Location l
        ORDER BY l.name ASC
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            l.id AS id,
            l.name AS name,
            l.description AS description,
            NULL AS shelves
        FROM Location l
        WHERE l.id = :id
    """)
    Optional<LocationDetailResponse> findDetailById(Long id);
}
