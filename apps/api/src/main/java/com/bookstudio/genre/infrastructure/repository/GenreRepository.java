package com.bookstudio.genre.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstudio.genre.domain.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
