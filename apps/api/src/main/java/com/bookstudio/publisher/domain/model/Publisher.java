package com.bookstudio.publisher.domain.model;

import com.bookstudio.nationality.domain.model.Nationality;
import com.bookstudio.shared.type.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "publishers")
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nationality_id", nullable = false)
    private Nationality nationality;

    @Column(name = "foundation_year", nullable = false)
    private Integer foundationYear;

    private String website;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 512)
    private String photoUrl;
}
