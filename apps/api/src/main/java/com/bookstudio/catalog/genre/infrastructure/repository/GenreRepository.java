package com.bookstudio.catalog.genre.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstudio.catalog.genre.domain.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
