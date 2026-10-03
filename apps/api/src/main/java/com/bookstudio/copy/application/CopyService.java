package com.bookstudio.copy.application;

import com.bookstudio.book.BookApi;
import com.bookstudio.copy.CopyApi;
import com.bookstudio.copy.CopyStatus;
import com.bookstudio.copy.application.dto.request.CreateCopyRequest;
import com.bookstudio.copy.application.dto.request.UpdateCopyRequest;
import com.bookstudio.copy.application.dto.response.CopyDetailResponse;
import com.bookstudio.copy.application.dto.response.CopyFilterOptionsResponse;
import com.bookstudio.copy.application.dto.response.CopyListResponse;
import com.bookstudio.copy.application.dto.response.CopySelectOptionsResponse;
import com.bookstudio.copy.domain.model.Copy;
import com.bookstudio.copy.domain.model.type.CopyCondition;
import com.bookstudio.copy.infrastructure.repository.CopyRepository;
import com.bookstudio.location.LocationApi;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.response.OptionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CopyService implements CopyApi {
    private final CodeGenerator codeGenerator;

    private final CopyRepository copyRepository;
    private final BookApi bookApi;
    private final LocationApi locationApi;

    @Override
    public void requireExists(Long id) {
        if (!copyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Copy not found with ID: " + id);
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return copyRepository.findForOptions();
    }

    @Override
    @Transactional
    public void lend(Collection<Long> copyIds) {
        loadAll(copyIds).forEach(Copy::lend);
    }

    @Override
    @Transactional
    public void release(Collection<Long> copyIds) {
        loadAll(copyIds).forEach(Copy::release);
    }

    @Override
    @Transactional
    public void markLost(Collection<Long> copyIds) {
        loadAll(copyIds).forEach(Copy::markLost);
    }

    private List<Copy> loadAll(Collection<Long> copyIds) {
        Map<Long, Copy> copies = copyRepository.findAllById(copyIds).stream()
                .collect(Collectors.toMap(Copy::getId, Function.identity()));

        return copyIds.stream()
                .map(id -> Optional.ofNullable(copies.get(id))
                        .orElseThrow(() -> new ResourceNotFoundException("Copy not found with ID: " + id)))
                .toList();
    }

    public List<CopyListResponse> getList() {
        return copyRepository.findList();
    }

    public CopyFilterOptionsResponse getFilterOptions() {
        return new CopyFilterOptionsResponse(
                bookApi.getOptions());
    }

    public CopySelectOptionsResponse getSelectOptions() {
        return new CopySelectOptionsResponse(
                bookApi.getOptions(),
                locationApi.getShelfOptions());
    }

    public CopyDetailResponse getDetailById(Long id) {
        return copyRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found with ID: " + id));
    }

    @Transactional
    public CopyListResponse create(CreateCopyRequest request) {
        Copy copy = new Copy();
        bookApi.requireExists(request.bookId());
        locationApi.requireShelfExists(request.shelfId());

        copy.setBookId(request.bookId());
        copy.setShelfId(request.shelfId());

        copy.setBarcode(request.barcode());
        copy.setStatus(CopyStatus.valueOf(request.status()));
        copy.setCondition(CopyCondition.valueOf(request.condition()));

        copy.setCode(codeGenerator.next(Copy.CODE_SERIES, LocalDate.now()));

        Copy saved = copyRepository.save(copy);

        return toListResponse(saved);
    }

    @Transactional
    public CopyListResponse update(Long id, UpdateCopyRequest request) {
        Copy copy = copyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found with ID: " + id));

        locationApi.requireShelfExists(request.shelfId());
        copy.setShelfId(request.shelfId());

        copy.setBarcode(request.barcode());
        copy.setStatus(CopyStatus.valueOf(request.status()));
        copy.setCondition(CopyCondition.valueOf(request.condition()));

        Copy updated = copyRepository.save(copy);

        return toListResponse(updated);
    }

    private CopyListResponse toListResponse(Copy copy) {
        return copyRepository.findListItemById(copy.getId()).orElseThrow();
    }
}
