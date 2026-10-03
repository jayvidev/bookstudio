package com.bookstudio.genre.application;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.genre.GenreApi;
import com.bookstudio.genre.domain.model.Genre;
import com.bookstudio.genre.infrastructure.repository.GenreRepository;
import com.bookstudio.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GenreService implements GenreApi {
    private final GenreRepository genreRepository;

    @Override
    public void requireAllExist(Collection<Long> ids) {
        Set<Long> found = new HashSet<>();
        genreRepository.findAllById(ids).stream().map(Genre::getId).forEach(found::add);

        ids.stream()
                .filter(id -> !found.contains(id))
                .findFirst()
                .ifPresent(id -> {
                    throw new ResourceNotFoundException("Genre not found with ID: " + id);
                });
    }
}
