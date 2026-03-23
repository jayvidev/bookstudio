package com.bookstudio.publisher.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherGenreId implements Serializable {
    @Column(name = "publisher_id")
    private Long publisherId;

    @Column(name = "genre_id")
    private Long genreId;
}
