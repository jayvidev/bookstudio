package com.bookstudio.author.application;

import com.bookstudio.author.application.dto.request.CreateAuthorRequest;
import com.bookstudio.author.application.dto.request.UpdateAuthorRequest;
import com.bookstudio.author.application.dto.response.AuthorDetailResponse;
import com.bookstudio.author.application.dto.response.AuthorFilterOptionsResponse;
import com.bookstudio.author.application.dto.response.AuthorListResponse;
import com.bookstudio.author.application.dto.response.AuthorSelectOptionsResponse;
import com.bookstudio.author.domain.model.Author;
import com.bookstudio.author.infrastructure.repository.AuthorRepository;
import com.bookstudio.nationality.NationalityApi;
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
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final NationalityApi nationalityApi;

    public List<AuthorListResponse> getList() {
        return authorRepository.findList();
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
