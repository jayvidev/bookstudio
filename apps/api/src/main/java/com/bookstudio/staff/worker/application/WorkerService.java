package com.bookstudio.staff.worker.application;

import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.exception.BusinessRuleException;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.PageProjection;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;
import com.bookstudio.staff.role.RoleApi;
import com.bookstudio.staff.worker.application.dto.request.CreateWorkerRequest;
import com.bookstudio.staff.worker.application.dto.request.UpdateWorkerRequest;
import com.bookstudio.staff.worker.application.dto.request.WorkerFilter;
import com.bookstudio.staff.worker.application.dto.response.WorkerDetailResponse;
import com.bookstudio.staff.worker.application.dto.response.WorkerFilterOptionsResponse;
import com.bookstudio.staff.worker.application.dto.response.WorkerListResponse;
import com.bookstudio.staff.worker.domain.model.Worker;
import com.bookstudio.staff.worker.domain.model.type.WorkerStatus;
import com.bookstudio.staff.worker.infrastructure.repository.WorkerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class WorkerService {
    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "username", "lastName");

    private final WorkerRepository workerRepository;
    private final RoleApi roleApi;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param excludedId the caller, who manages their own account elsewhere
     */
    public PageResponse<WorkerListResponse> getPage(WorkerFilter filter, Pageable pageable, Long excludedId) {
        Specification<Worker> spec = Specification.allOf(
                filter.search() == null || filter.search().isBlank()
                        ? Specification.unrestricted()
                        : Specification.anyOf(
                                Specs.containsIgnoreCase(filter.search(), "username", "email"),
                                Specs.fullNameContains(filter.search(), "firstName", "lastName")),
                Specs.equal("roleId", filter.roleId()),
                Specs.equal("status", filter.status()),
                Specs.notEqual("id", excludedId));

        return PageProjection.of(
                workerRepository.findAll(spec, SORTABLE.validate(pageable)),
                Worker::getId,
                workerRepository::findListByIds,
                WorkerListResponse::id);
    }

    public WorkerFilterOptionsResponse getFilterOptions() {
        return new WorkerFilterOptionsResponse(roleApi.getOptions());
    }

    public WorkerDetailResponse getDetailById(Long id) {
        return workerRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found with ID: " + id));
    }

    @Transactional
    public WorkerListResponse create(CreateWorkerRequest request) {
        if (workerRepository.findByUsername(request.username()).isPresent()) {
            throw new BusinessRuleException("The provided username is already registered.");
        }

        if (workerRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessRuleException("The provided email address is already registered.");
        }

        Worker worker = new Worker();
        roleApi.requireExists(request.roleId());
        worker.setRoleId(request.roleId());

        worker.setUsername(request.username());
        worker.setEmail(request.email());
        worker.setFirstName(request.firstName());
        worker.setLastName(request.lastName());
        worker.setPassword(passwordEncoder.encode(request.password()));
        worker.setProfilePhotoUrl(request.profilePhotoUrl());
        worker.setStatus(WorkerStatus.valueOf(request.status()));

        Worker saved = workerRepository.save(worker);
        return toListResponse(saved);
    }

    @Transactional
    public WorkerListResponse update(Long id, UpdateWorkerRequest request) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker not found with ID: " + id));

        roleApi.requireExists(request.roleId());
        worker.setRoleId(request.roleId());

        worker.setFirstName(request.firstName());
        worker.setLastName(request.lastName());
        worker.setProfilePhotoUrl(request.profilePhotoUrl());
        worker.setStatus(WorkerStatus.valueOf(request.status()));

        Worker updated = workerRepository.save(worker);
        return toListResponse(updated);
    }

    private WorkerListResponse toListResponse(Worker worker) {
        return workerRepository.findListItemById(worker.getId()).orElseThrow();
    }
}
