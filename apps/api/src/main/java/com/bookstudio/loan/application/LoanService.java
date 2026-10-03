package com.bookstudio.loan.application;

import com.bookstudio.book.BookApi;
import com.bookstudio.copy.CopyApi;
import com.bookstudio.loan.LoanApi;
import com.bookstudio.loan.LoanItemStatus;
import com.bookstudio.loan.application.dto.request.CreateLoanItemRequest;
import com.bookstudio.loan.application.dto.request.CreateLoanRequest;
import com.bookstudio.loan.application.dto.request.LoanFilter;
import com.bookstudio.loan.application.dto.request.UpdateLoanItemRequest;
import com.bookstudio.loan.application.dto.request.UpdateLoanRequest;
import com.bookstudio.loan.application.dto.response.LoanDetailResponse;
import com.bookstudio.loan.application.dto.response.LoanFilterOptionsResponse;
import com.bookstudio.loan.application.dto.response.LoanListResponse;
import com.bookstudio.loan.application.dto.response.LoanSelectOptionsResponse;
import com.bookstudio.loan.domain.model.Loan;
import com.bookstudio.loan.domain.model.LoanItem;
import com.bookstudio.loan.domain.model.LoanItemId;
import com.bookstudio.loan.infrastructure.repository.LoanItemRepository;
import com.bookstudio.loan.infrastructure.repository.LoanRepository;
import com.bookstudio.loan.infrastructure.repository.LoanSpecifications;
import com.bookstudio.reader.ReaderApi;
import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.response.OptionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class LoanService implements LoanApi {
    private final CodeGenerator codeGenerator;

    private final LoanRepository loanRepository;
    private final LoanItemRepository loanItemRepository;

    private final BookApi bookApi;
    private final ReaderApi readerApi;
    private final CopyApi copyApi;

    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "code", "loanDate");

    @Override
    public void requireItemExists(Long loanId, Long copyId) {
        if (!loanItemRepository.existsById(new LoanItemId(loanId, copyId))) {
            throw new ResourceNotFoundException("Loan item not found with ID: " + new LoanItemId(loanId, copyId));
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return loanRepository.findForOptions();
    }

    public PageResponse<LoanListResponse> getPage(LoanFilter filter, Pageable pageable) {
        Specification<Loan> spec = Specification.allOf(
                LoanSpecifications.hasItemWithStatus(filter.status()),
                LoanSpecifications.belongsToReader(filter.readerId()),
                LoanSpecifications.loanedOnOrAfter(filter.from()),
                LoanSpecifications.loanedOnOrBefore(filter.to()),
                filter.hasSearch()
                        ? LoanSpecifications.codeContainsOrReaderIn(
                                filter.search(), readerApi.findIdsByName(filter.search().trim()))
                        : Specification.unrestricted());

        Page<Loan> page = loanRepository.findAll(spec, SORTABLE.validate(pageable));
        return PageResponse.from(page, this::toListResponses);
    }

    public LoanFilterOptionsResponse getFilterOptions() {
        return new LoanFilterOptionsResponse(
                readerApi.getOptions());
    }

    public LoanSelectOptionsResponse getSelectOptions() {
        return new LoanSelectOptionsResponse(
                bookApi.getOptions(),
                readerApi.getOptions());
    }

    public LoanDetailResponse getDetailById(Long id) {
        LoanDetailResponse base = loanRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        return base.withItems(loanItemRepository.findLoanItemsByLoanId(id));
    }

    @Transactional
    public LoanListResponse create(CreateLoanRequest request) {
        Loan loan = new Loan();
        readerApi.requireExists(request.readerId());
        loan.setReaderId(request.readerId());

        loan.setLoanDate(LocalDate.now());
        loan.setObservation(request.observation());

        loan.setCode(codeGenerator.next(Loan.CODE_SERIES, loan.getLoanDate()));

        Loan saved = loanRepository.save(loan);

        for (CreateLoanItemRequest itemDto : request.items()) {
            copyApi.requireExists(itemDto.copyId());

            LoanItem item = new LoanItem(
                    new LoanItemId(saved.getId(), itemDto.copyId()),
                    saved,
                    itemDto.dueDate(),
                    null,
                    LoanItemStatus.PRESTADO);

            saved.getLoanItems().add(loanItemRepository.save(item));
        }

        return toListResponse(saved);
    }

    @Transactional
    public LoanListResponse update(Long id, UpdateLoanRequest request) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        readerApi.requireExists(request.readerId());
        loan.setReaderId(request.readerId());

        loan.setObservation(request.observation());

        Loan updated = loanRepository.save(loan);

        loanItemRepository.deleteAllByLoan(updated);

        for (UpdateLoanItemRequest itemDto : request.items()) {
            copyApi.requireExists(itemDto.copyId());

            LoanItem item = new LoanItem(
                    new LoanItemId(updated.getId(), itemDto.copyId()),
                    updated,
                    itemDto.dueDate(),
                    null,
                    LoanItemStatus.PRESTADO);

            loanItemRepository.save(item);
        }

        return toListResponse(updated);
    }

    /**
     * Projects one page of loans with a single query, keeping the page order.
     */
    private List<LoanListResponse> toListResponses(List<Loan> loans) {
        if (loans.isEmpty()) {
            return List.of();
        }

        Map<Long, LoanListResponse> byId = loanRepository.findListByIds(loans.stream().map(Loan::getId).toList())
                .stream()
                .collect(Collectors.toMap(LoanListResponse::id, Function.identity()));

        return loans.stream().map(loan -> byId.get(loan.getId())).toList();
    }

    private LoanListResponse toListResponse(Loan loan) {
        return loanRepository.findListItemById(loan.getId()).orElseThrow();
    }
}
