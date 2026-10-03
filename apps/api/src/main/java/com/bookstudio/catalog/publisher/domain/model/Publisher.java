package com.bookstudio.catalog.publisher.domain.model;

import com.bookstudio.shared.type.Status;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publishers")
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "nationality_id", nullable = false)
    private Long nationalityId;

    @Column(name = "foundation_year", nullable = false)
    private Integer foundationYear;

    private String website;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 512)
    private String photoUrl;

    @ElementCollection
    @CollectionTable(name = "publisher_genres", joinColumns = @JoinColumn(name = "publisher_id"))
    @Column(name = "genre_id")
    private Set<Long> genreIds = new HashSet<>();

    public void replaceGenres(Collection<Long> ids) {
        genreIds.clear();
        genreIds.addAll(ids);
    }
}
