package com.bookstudio.billing.fine.application;

import com.bookstudio.billing.fine.FineApi;
import com.bookstudio.billing.fine.FineStatus;
import com.bookstudio.billing.fine.application.dto.request.CreateFineRequest;
import com.bookstudio.billing.fine.application.dto.request.FineFilter;
import com.bookstudio.billing.fine.application.dto.request.UpdateFineRequest;
import com.bookstudio.billing.fine.application.dto.response.FineDetailResponse;
import com.bookstudio.billing.fine.application.dto.response.FineFilterOptionsResponse;
import com.bookstudio.billing.fine.application.dto.response.FineListResponse;
import com.bookstudio.billing.fine.domain.model.Fine;
import com.bookstudio.billing.fine.infrastructure.repository.FineRepository;
import com.bookstudio.circulation.LoanApi;
import com.bookstudio.inventory.CopyApi;
import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.PageProjection;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class FineService implements FineApi {
    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "code", "issuedAt", "amount");

    private final CodeGenerator codeGenerator;

    private final FineRepository fineRepository;
    private final LoanApi loanApi;
    private final CopyApi copyApi;

    @Override
    @Transactional
    public void markPaid(Collection<Long> fineIds) {
        Map<Long, Fine> fines = fineRepository.findAllById(fineIds).stream()
                .collect(Collectors.toMap(Fine::getId, Function.identity()));

        for (Long fineId : fineIds) {
            Fine fine = fines.get(fineId);
            if (fine == null) {
                throw new ResourceNotFoundException("Fine not found with ID: " + fineId);
            }
            fine.setStatus(FineStatus.PAGADO);
        }
    }

    public PageResponse<FineListResponse> getPage(FineFilter filter, Pageable pageable) {
        Specification<Fine> spec = Specification.allOf(
                Specs.containsIgnoreCase(filter.search(), "code"),
                Specs.equal("loanId", filter.loanId()),
                Specs.equal("status", filter.status()),
                Specs.onOrAfter("issuedAt", filter.from()),
                Specs.onOrBefore("issuedAt", filter.to()));

        return PageProjection.of(
                fineRepository.findAll(spec, SORTABLE.validate(pageable)),
                Fine::getId,
                fineRepository::findListByIds,
                FineListResponse::id);
    }

    public FineFilterOptionsResponse getFilterOptions() {
        return new FineFilterOptionsResponse(
                loanApi.getOptions(),
                copyApi.getOptions());
    }

    public FineDetailResponse getDetailById(Long id) {
        return fineRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with ID: " + id));
    }

    @Transactional
    public FineListResponse create(CreateFineRequest request) {
        Fine fine = new Fine();
        loanApi.requireItemExists(request.loanItemId().loanId(), request.loanItemId().copyId());
        fine.setLoanId(request.loanItemId().loanId());
        fine.setCopyId(request.loanItemId().copyId());

        fine.setAmount(request.amount());
        fine.setDaysLate(request.daysLate());
        fine.setStatus(FineStatus.valueOf(request.status()));
        fine.setIssuedAt(request.issuedAt());

        fine.setCode(codeGenerator.next(Fine.CODE_SERIES, fine.getIssuedAt()));

        Fine saved = fineRepository.save(fine);

        return toListResponse(saved);
    }

    @Transactional
    public FineListResponse update(Long id, UpdateFineRequest request) {
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with ID: " + id));

        fine.setAmount(request.amount());
        fine.setDaysLate(request.daysLate());
        fine.setStatus(FineStatus.valueOf(request.status()));

        Fine updated = fineRepository.save(fine);

        return toListResponse(updated);
    }

    private FineListResponse toListResponse(Fine fine) {
        return fineRepository.findListItemById(fine.getId()).orElseThrow();
    }
}
