package com.bookstudio.publisher.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.publisher.application.dto.response.PublisherDetailResponse;
import com.bookstudio.publisher.domain.model.Publisher;
import com.bookstudio.publisher.domain.model.PublisherGenre;
import com.bookstudio.publisher.domain.model.PublisherGenreId;

public interface PublisherGenreRepository extends JpaRepository<PublisherGenre, PublisherGenreId> {
    @Query("""
        SELECT 
            g.id AS id,
            g.name AS name
        FROM PublisherGenre pg
        JOIN pg.genre g
        WHERE pg.publisher.id = :id
    """)
    List<PublisherDetailResponse.GenreItem> findGenreItemsByPublisherId(Long id);

    void deleteAllByPublisher(Publisher publisher);
}
