package com.bookstudio.publisher.application;

import com.bookstudio.genre.domain.model.Genre;
import com.bookstudio.nationality.infrastructure.repository.NationalityRepository;
import com.bookstudio.publisher.application.dto.request.CreatePublisherRequest;
import com.bookstudio.publisher.application.dto.request.UpdatePublisherRequest;
import com.bookstudio.publisher.application.dto.response.PublisherDetailResponse;
import com.bookstudio.publisher.application.dto.response.PublisherFilterOptionsResponse;
import com.bookstudio.publisher.application.dto.response.PublisherListResponse;
import com.bookstudio.publisher.application.dto.response.PublisherSelectOptionsResponse;
import com.bookstudio.publisher.domain.model.Publisher;
import com.bookstudio.publisher.domain.model.PublisherGenre;
import com.bookstudio.publisher.domain.model.PublisherGenreId;
import com.bookstudio.publisher.infrastructure.repository.PublisherGenreRepository;
import com.bookstudio.publisher.infrastructure.repository.PublisherRepository;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.type.Status;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherGenreRepository publisherGenreRepository;
    private final NationalityRepository nationalityRepository;

    public List<PublisherListResponse> getList() {
        return publisherRepository.findList();
    }

    public PublisherFilterOptionsResponse getFilterOptions() {
        return new PublisherFilterOptionsResponse(
                nationalityRepository.findForOptions());
    }

    public PublisherSelectOptionsResponse getSelectOptions() {
        return new PublisherSelectOptionsResponse(
                nationalityRepository.findForOptions());
    }

    public PublisherDetailResponse getDetailById(Long id) {
        PublisherDetailResponse base = publisherRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + id));

        return base.withGenres(publisherGenreRepository.findGenreItemsByPublisherId(id));
    }

    @Transactional
    public PublisherListResponse create(CreatePublisherRequest request) {
        Publisher publisher = new Publisher();
        publisher.setNationality(nationalityRepository.findById(request.nationalityId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Nationality not found with ID: " + request.nationalityId())));

        publisher.setName(request.name());
        publisher.setFoundationYear(request.foundationYear());
        publisher.setWebsite(request.website());
        publisher.setAddress(request.address());
        publisher.setStatus(Status.valueOf(request.status()));
        publisher.setPhotoUrl(request.photoUrl());

        Publisher saved = publisherRepository.save(publisher);

        for (Long genreId : request.genreIds()) {
            PublisherGenre relation = new PublisherGenre(
                    new PublisherGenreId(saved.getId(), genreId),
                    saved,
                    new Genre(genreId));
            publisherGenreRepository.save(relation);
        }

        return toListResponse(saved);
    }

    @Transactional
    public PublisherListResponse update(Long id, UpdatePublisherRequest request) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + id));

        publisher.setNationality(nationalityRepository.findById(request.nationalityId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Nationality not found with ID: " + request.nationalityId())));

        publisher.setName(request.name());
        publisher.setFoundationYear(request.foundationYear());
        publisher.setWebsite(request.website());
        publisher.setAddress(request.address());
        publisher.setStatus(Status.valueOf(request.status()));

        if (request.photoUrl() == null || request.photoUrl().isBlank()) {
            publisher.setPhotoUrl(null);
        } else {
            publisher.setPhotoUrl(request.photoUrl());
        }

        Publisher updated = publisherRepository.save(publisher);

        publisherGenreRepository.deleteAllByPublisher(updated);

        for (Long genreId : request.genreIds()) {
            PublisherGenre relation = new PublisherGenre(
                    new PublisherGenreId(updated.getId(), genreId),
                    updated,
                    new Genre(genreId));
            publisherGenreRepository.save(relation);
        }

        return toListResponse(updated);
    }

    private PublisherListResponse toListResponse(Publisher publisher) {
        return new PublisherListResponse(
                publisher.getId(),
                publisher.getPhotoUrl(),
                publisher.getName(),

                publisher.getNationality().getId(),
                publisher.getNationality().getCode(),
                publisher.getNationality().getName(),

                publisher.getWebsite(),
                publisher.getAddress(),
                publisher.getStatus());
    }
}
