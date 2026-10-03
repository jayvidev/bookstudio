package com.bookstudio.circulation.loan.application;

import com.bookstudio.catalog.BookApi;
import com.bookstudio.inventory.CopyApi;
import com.bookstudio.circulation.LoanApi;
import com.bookstudio.circulation.LoanItemStatus;
import com.bookstudio.circulation.loan.application.dto.request.CreateLoanItemRequest;
import com.bookstudio.circulation.loan.application.dto.request.CreateLoanRequest;
import com.bookstudio.circulation.loan.application.dto.request.LoanFilter;
import com.bookstudio.circulation.loan.application.dto.request.UpdateLoanItemRequest;
import com.bookstudio.circulation.loan.application.dto.request.UpdateLoanRequest;
import com.bookstudio.circulation.loan.application.dto.response.LoanDetailResponse;
import com.bookstudio.circulation.loan.application.dto.response.LoanFilterOptionsResponse;
import com.bookstudio.circulation.loan.application.dto.response.LoanListResponse;
import com.bookstudio.circulation.loan.application.dto.response.LoanSelectOptionsResponse;
import com.bookstudio.circulation.loan.domain.model.Loan;
import com.bookstudio.circulation.loan.domain.model.LoanItem;
import com.bookstudio.circulation.loan.domain.model.LoanItemId;
import com.bookstudio.circulation.loan.infrastructure.repository.LoanItemRepository;
import com.bookstudio.circulation.loan.infrastructure.repository.LoanRepository;
import com.bookstudio.circulation.loan.infrastructure.repository.LoanSpecifications;
import com.bookstudio.membership.ReaderApi;
import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.BadRequestException;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;
import com.bookstudio.shared.response.OptionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                Specs.equal("readerId", filter.readerId()),
                Specs.onOrAfter("loanDate", filter.from()),
                Specs.onOrBefore("loanDate", filter.to()),
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
        readerApi.requireExists(request.readerId());
        requireDistinctCopies(request.items().stream().map(CreateLoanItemRequest::copyId).toList());

        LocalDate today = LocalDate.now();
        Loan loan = Loan.open(
                codeGenerator.next(Loan.CODE_SERIES, today), request.readerId(), today, request.observation());

        CopyChanges copyChanges = new CopyChanges();
        for (CreateLoanItemRequest item : request.items()) {
            copyChanges.record(item.copyId(), loan.addItem(item.copyId(), item.dueDate()));
        }
        copyChanges.applyTo(copyApi);

        return toListResponse(loanRepository.save(loan));
    }

    /**
     * Applies the requested items as a diff: kept items are changed in place
     * (keeping their history), missing ones are removed and new ones added.
     * Copies follow: returned or removed copies go back to the shelf, lost ones
     * are marked lost, new ones are lent.
     */
    @Transactional
    public LoanListResponse update(Long id, UpdateLoanRequest request) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with ID: " + id));

        readerApi.requireExists(request.readerId());
        requireDistinctCopies(request.items().stream().map(UpdateLoanItemRequest::copyId).toList());
        loan.updateDetails(request.readerId(), request.observation());

        Map<Long, UpdateLoanItemRequest> requested = request.items().stream()
                .collect(Collectors.toMap(UpdateLoanItemRequest::copyId, Function.identity()));
        LocalDate today = LocalDate.now();
        CopyChanges copyChanges = new CopyChanges();

        loan.getLoanItems().stream()
                .map(LoanItem::getCopyId)
                .filter(copyId -> !requested.containsKey(copyId))
                .toList()
                .forEach(copyId -> copyChanges.record(copyId, loan.removeItem(copyId)));

        for (UpdateLoanItemRequest item : request.items()) {
            LoanItemStatus status = LoanItemStatus.valueOf(item.status());

            if (loan.findItem(item.copyId()).isPresent()) {
                copyChanges.record(item.copyId(), loan.changeItem(item.copyId(), item.dueDate(), status, today));
            } else if (status == LoanItemStatus.PRESTADO) {
                copyChanges.record(item.copyId(), loan.addItem(item.copyId(), item.dueDate()));
            } else {
                throw new BadRequestException(
                        "Copy %d is new to this loan, so its status must be PRESTADO".formatted(item.copyId()));
            }
        }
        copyChanges.applyTo(copyApi);

        return toListResponse(loanRepository.save(loan));
    }

    private static void requireDistinctCopies(List<Long> copyIds) {
        Set<Long> seen = new HashSet<>();
        for (Long copyId : copyIds) {
            if (!seen.add(copyId)) {
                throw new BadRequestException("Copy %d appears more than once in the loan".formatted(copyId));
            }
        }
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
