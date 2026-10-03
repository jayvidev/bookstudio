package com.bookstudio.fine.application;

import com.bookstudio.copy.CopyApi;
import com.bookstudio.fine.application.dto.request.CreateFineRequest;
import com.bookstudio.fine.application.dto.request.UpdateFineRequest;
import com.bookstudio.fine.application.dto.response.FineDetailResponse;
import com.bookstudio.fine.application.dto.response.FineFilterOptionsResponse;
import com.bookstudio.fine.application.dto.response.FineListResponse;
import com.bookstudio.fine.domain.model.Fine;
import com.bookstudio.fine.domain.model.type.FineStatus;
import com.bookstudio.fine.infrastructure.repository.FineRepository;
import com.bookstudio.loan.LoanApi;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class FineService {
    private final CodeGenerator codeGenerator;

    private final FineRepository fineRepository;
    private final LoanApi loanApi;
    private final CopyApi copyApi;

    public List<FineListResponse> getList() {
        return fineRepository.findList();
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
