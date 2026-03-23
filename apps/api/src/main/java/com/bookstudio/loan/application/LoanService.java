package com.bookstudio.loan.application;

import com.bookstudio.book.infrastructure.repository.BookRepository;
import com.bookstudio.copy.domain.model.Copy;
import com.bookstudio.copy.infrastructure.repository.CopyRepository;
import com.bookstudio.loan.application.dto.request.CreateLoanItemRequest;
import com.bookstudio.loan.application.dto.request.CreateLoanRequest;
import com.bookstudio.loan.application.dto.request.UpdateLoanItemRequest;
import com.bookstudio.loan.application.dto.request.UpdateLoanRequest;
import com.bookstudio.loan.application.dto.response.LoanDetailResponse;
import com.bookstudio.loan.application.dto.response.LoanFilterOptionsResponse;
import com.bookstudio.loan.application.dto.response.LoanListResponse;
import com.bookstudio.loan.application.dto.response.LoanSelectOptionsResponse;
import com.bookstudio.loan.domain.model.Loan;
import com.bookstudio.loan.domain.model.LoanItem;
import com.bookstudio.loan.domain.model.LoanItemId;
import com.bookstudio.loan.domain.model.type.LoanItemStatus;
import com.bookstudio.loan.infrastructure.repository.LoanItemRepository;
import com.bookstudio.loan.infrastructure.repository.LoanRepository;
import com.bookstudio.reader.infrastructure.repository.ReaderRepository;
import com.bookstudio.shared.exception.ResourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class LoanService {
    @PersistenceContext
    private EntityManager entityManager;

    private final LoanRepository loanRepository;
    private final LoanItemRepository loanItemRepository;

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final CopyRepository copyRepository;

    public List<LoanListResponse> getList() {
        return loanRepository.findList();
    }

    public LoanFilterOptionsResponse getFilterOptions() {
        return new LoanFilterOptionsResponse(
                readerRepository.findForOptions());
    }

    public LoanSelectOptionsResponse getSelectOptions() {
        return new LoanSelectOptionsResponse(
                bookRepository.findForOptions(),
                readerRepository.findForOptions());
    }

    public LoanDetailResponse getDetailById(Long id) {
        LoanDetailResponse base = loanRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        return base.withItems(loanItemRepository.findLoanItemsByLoanId(id));
    }

    @Transactional
    public LoanListResponse create(CreateLoanRequest request) {
        Loan loan = new Loan();
        loan.setReader(readerRepository.findById(request.readerId())
                .orElseThrow(() -> new ResourceNotFoundException("Reader not found with ID: " + request.readerId())));

        loan.setLoanDate(LocalDate.now());
        loan.setObservation(request.observation());

        Loan saved = loanRepository.save(loan);
        entityManager.refresh(saved);

        for (CreateLoanItemRequest itemDto : request.items()) {
            Copy copy = copyRepository.findById(itemDto.copyId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Copy not found with ID: " + itemDto.copyId()));

            LoanItem item = new LoanItem(
                    new LoanItemId(saved.getId(), copy.getId()),
                    saved,
                    copy,
                    itemDto.dueDate(),
                    null,
                    LoanItemStatus.PRESTADO);

            loanItemRepository.save(item);
        }

        return toListResponse(saved);
    }

    @Transactional
    public LoanListResponse update(Long id, UpdateLoanRequest request) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        loan.setReader(readerRepository.findById(request.readerId())
                .orElseThrow(() -> new ResourceNotFoundException("Reader not found with ID: " + request.readerId())));

        loan.setObservation(request.observation());

        Loan updated = loanRepository.save(loan);

        loanItemRepository.deleteAllByLoan(updated);

        for (UpdateLoanItemRequest itemDto : request.items()) {
            Copy copy = copyRepository.findById(itemDto.copyId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Copy not found with ID: " + itemDto.copyId()));

            LoanItem item = new LoanItem(
                    new LoanItemId(updated.getId(), copy.getId()),
                    updated,
                    copy,
                    itemDto.dueDate(),
                    null,
                    LoanItemStatus.PRESTADO);

            loanItemRepository.save(item);
        }

        return toListResponse(updated);
    }

    public LoanListResponse toListResponse(Loan loan) {
        Map<LoanItemStatus, Long> counts = loan.getLoanItems().stream()
                .collect(Collectors.groupingBy(
                        LoanItem::getStatus,
                        Collectors.counting()));

        Long borrowed = counts.getOrDefault(LoanItemStatus.PRESTADO, 0L);
        Long returned = counts.getOrDefault(LoanItemStatus.DEVUELTO, 0L);
        Long overdue = counts.getOrDefault(LoanItemStatus.RETRASADO, 0L);
        Long lost = counts.getOrDefault(LoanItemStatus.EXTRAVIADO, 0L);
        Long canceled = counts.getOrDefault(LoanItemStatus.CANCELADO, 0L);

        return new LoanListResponse(
                loan.getId(),
                loan.getCode(),

                loan.getReader().getId(),
                loan.getReader().getCode(),
                loan.getReader().getFullName(),

                loan.getLoanDate(),
                borrowed + returned + overdue + lost + canceled,

                borrowed,
                returned,
                overdue,
                lost,
                canceled);
    }
}
