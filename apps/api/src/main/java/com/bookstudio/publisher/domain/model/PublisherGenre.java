package com.bookstudio.publisher.domain.model;

import com.bookstudio.genre.domain.model.Genre;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "publisher_genres")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherGenre {
    @EmbeddedId
    private PublisherGenreId id;

    @ManyToOne
    @MapsId("publisherId")
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
