package com.bookstudio.copy.application;

import com.bookstudio.book.infrastructure.repository.BookRepository;
import com.bookstudio.copy.infrastructure.repository.CopyRepository;
import com.bookstudio.location.infrastructure.repository.ShelfRepository;
import com.bookstudio.copy.application.dto.request.CreateCopyRequest;
import com.bookstudio.copy.application.dto.request.UpdateCopyRequest;
import com.bookstudio.copy.application.dto.response.CopyDetailResponse;
import com.bookstudio.copy.application.dto.response.CopyFilterOptionsResponse;
import com.bookstudio.copy.application.dto.response.CopyListResponse;
import com.bookstudio.copy.application.dto.response.CopySelectOptionsResponse;
import com.bookstudio.copy.domain.model.Copy;
import com.bookstudio.copy.domain.model.type.CopyCondition;
import com.bookstudio.copy.domain.model.type.CopyStatus;
import com.bookstudio.shared.exception.ResourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CopyService {
    @PersistenceContext
    private EntityManager entityManager;

    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;

    public List<CopyListResponse> getList() {
        return copyRepository.findList();
    }

    public CopyFilterOptionsResponse getFilterOptions() {
        return new CopyFilterOptionsResponse(
                bookRepository.findForOptions());
    }

    public CopySelectOptionsResponse getSelectOptions() {
        return new CopySelectOptionsResponse(
                bookRepository.findForOptions(),
                shelfRepository.findForOptions());
    }

    public CopyDetailResponse getDetailById(Long id) {
        return copyRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found with ID: " + id));
    }

    @Transactional
    public CopyListResponse create(CreateCopyRequest request) {
        Copy copy = new Copy();
        copy.setBook(bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + request.bookId())));
        copy.setShelf(shelfRepository.findById(request.shelfId())
                .orElseThrow(() -> new ResourceNotFoundException("Shelf not found with ID: " + request.shelfId())));

        copy.setBarcode(request.barcode());
        copy.setStatus(CopyStatus.valueOf(request.status()));
        copy.setCondition(CopyCondition.valueOf(request.condition()));

        Copy saved = copyRepository.save(copy);
        entityManager.refresh(saved);

        return toListResponse(saved);
    }

    @Transactional
    public CopyListResponse update(Long id, UpdateCopyRequest request) {
        Copy copy = copyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Copy not found with ID: " + id));

        copy.setShelf(shelfRepository.findById(request.shelfId())
                .orElseThrow(() -> new ResourceNotFoundException("Shelf not found with ID: " + request.shelfId())));

        copy.setBarcode(request.barcode());
        copy.setStatus(CopyStatus.valueOf(request.status()));
        copy.setCondition(CopyCondition.valueOf(request.condition()));

        Copy updated = copyRepository.save(copy);

        return toListResponse(updated);
    }

    private CopyListResponse toListResponse(Copy copy) {
        return new CopyListResponse(
                copy.getId(),
                copy.getCode(),

                copy.getBook().getId(),
                copy.getBook().getCoverUrl(),
                copy.getBook().getTitle(),

                copy.getShelf().getCode(),
                copy.getShelf().getFloor(),

                copy.getShelf().getLocation().getName(),

                copy.getStatus(),
                copy.getCondition());
    }
}
