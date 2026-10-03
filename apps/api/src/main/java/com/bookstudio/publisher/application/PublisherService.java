package com.bookstudio.publisher.application;

import com.bookstudio.genre.GenreApi;
import com.bookstudio.nationality.NationalityApi;
import com.bookstudio.publisher.application.dto.request.CreatePublisherRequest;
import com.bookstudio.publisher.application.dto.request.UpdatePublisherRequest;
import com.bookstudio.publisher.application.dto.response.PublisherDetailResponse;
import com.bookstudio.publisher.application.dto.response.PublisherFilterOptionsResponse;
import com.bookstudio.publisher.application.dto.response.PublisherListResponse;
import com.bookstudio.publisher.application.dto.response.PublisherSelectOptionsResponse;
import com.bookstudio.publisher.domain.model.Publisher;
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
    private final NationalityApi nationalityApi;
    private final GenreApi genreApi;

    public List<PublisherListResponse> getList() {
        return publisherRepository.findList();
    }

    public PublisherFilterOptionsResponse getFilterOptions() {
        return new PublisherFilterOptionsResponse(
                nationalityApi.getOptions());
    }

    public PublisherSelectOptionsResponse getSelectOptions() {
        return new PublisherSelectOptionsResponse(
                nationalityApi.getOptions());
    }

    public PublisherDetailResponse getDetailById(Long id) {
        PublisherDetailResponse base = publisherRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + id));

        return base.withGenres(publisherRepository.findGenreItemsByPublisherId(id));
    }

    @Transactional
    public PublisherListResponse create(CreatePublisherRequest request) {
        Publisher publisher = new Publisher();
        nationalityApi.requireExists(request.nationalityId());
        genreApi.requireAllExist(request.genreIds());

        publisher.setNationalityId(request.nationalityId());
        publisher.replaceGenres(request.genreIds());

        publisher.setName(request.name());
        publisher.setFoundationYear(request.foundationYear());
        publisher.setWebsite(request.website());
        publisher.setAddress(request.address());
        publisher.setStatus(Status.valueOf(request.status()));
        publisher.setPhotoUrl(request.photoUrl());

        Publisher saved = publisherRepository.save(publisher);

        return toListResponse(saved);
    }

    @Transactional
    public PublisherListResponse update(Long id, UpdatePublisherRequest request) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + id));

        nationalityApi.requireExists(request.nationalityId());
        genreApi.requireAllExist(request.genreIds());

        publisher.setNationalityId(request.nationalityId());
        publisher.replaceGenres(request.genreIds());

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

        return toListResponse(updated);
    }

    private PublisherListResponse toListResponse(Publisher publisher) {
        return publisherRepository.findListItemById(publisher.getId()).orElseThrow();
    }
}
