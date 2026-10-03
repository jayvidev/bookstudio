package com.bookstudio.catalog.author.application;

import com.bookstudio.catalog.author.AuthorApi;
import com.bookstudio.catalog.author.application.dto.request.AuthorFilter;
import com.bookstudio.catalog.author.application.dto.request.CreateAuthorRequest;
import com.bookstudio.catalog.author.application.dto.request.UpdateAuthorRequest;
import com.bookstudio.catalog.author.application.dto.response.AuthorDetailResponse;
import com.bookstudio.catalog.author.application.dto.response.AuthorFilterOptionsResponse;
import com.bookstudio.catalog.author.application.dto.response.AuthorListResponse;
import com.bookstudio.catalog.author.application.dto.response.AuthorSelectOptionsResponse;
import com.bookstudio.catalog.author.domain.model.Author;
import com.bookstudio.catalog.author.infrastructure.repository.AuthorRepository;
import com.bookstudio.catalog.nationality.NationalityApi;
import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.PageProjection;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;
import com.bookstudio.shared.type.Status;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class AuthorService implements AuthorApi {
    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "name", "birthDate");

    private final AuthorRepository authorRepository;
    private final NationalityApi nationalityApi;

    @Override
    public void requireAllExist(Collection<Long> ids) {
        Set<Long> found = new HashSet<>();
        authorRepository.findAllById(ids).stream().map(Author::getId).forEach(found::add);

        ids.stream()
                .filter(id -> !found.contains(id))
                .findFirst()
                .ifPresent(id -> {
                    throw new ResourceNotFoundException("Author not found with ID: " + id);
                });
    }

    public PageResponse<AuthorListResponse> getPage(AuthorFilter filter, Pageable pageable) {
        Specification<Author> spec = Specification.allOf(
                Specs.containsIgnoreCase(filter.search(), "name"),
                Specs.equal("nationalityId", filter.nationalityId()),
                Specs.equal("status", filter.status()));

        return PageProjection.of(
                authorRepository.findAll(spec, SORTABLE.validate(pageable)),
                Author::getId,
                authorRepository::findListByIds,
                AuthorListResponse::id);
    }

    public AuthorFilterOptionsResponse getFilterOptions() {
        return new AuthorFilterOptionsResponse(
                nationalityApi.getOptions());
    }

    public AuthorSelectOptionsResponse getSelectOptions() {
        return new AuthorSelectOptionsResponse(
                nationalityApi.getOptions());
    }

    public AuthorDetailResponse getDetailById(Long id) {
        return authorRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + id));
    }

    @Transactional
    public AuthorListResponse create(CreateAuthorRequest request) {
        Author author = new Author();
        nationalityApi.requireExists(request.nationalityId());
        author.setNationalityId(request.nationalityId());

        author.setName(request.name());
        author.setBirthDate(request.birthDate());
        author.setBiography(request.biography());
        author.setStatus(Status.valueOf(request.status()));
        author.setPhotoUrl(request.photoUrl());

        Author saved = authorRepository.save(author);

        return toListResponse(saved);
    }

    @Transactional
    public AuthorListResponse update(Long id, UpdateAuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + id));

        nationalityApi.requireExists(request.nationalityId());
        author.setNationalityId(request.nationalityId());

        author.setName(request.name());
        author.setBirthDate(request.birthDate());
        author.setBiography(request.biography());
        author.setStatus(Status.valueOf(request.status()));
        author.setPhotoUrl(request.photoUrl());

        Author updated = authorRepository.save(author);

        return toListResponse(updated);
    }

    private AuthorListResponse toListResponse(Author author) {
        return authorRepository.findListItemById(author.getId()).orElseThrow();
    }
}
