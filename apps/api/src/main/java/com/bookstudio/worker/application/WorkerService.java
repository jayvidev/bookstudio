package com.bookstudio.worker.application;

import com.bookstudio.role.RoleApi;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.worker.application.dto.request.CreateWorkerRequest;
import com.bookstudio.worker.application.dto.request.UpdateWorkerRequest;
import com.bookstudio.worker.application.dto.response.WorkerDetailResponse;
import com.bookstudio.worker.application.dto.response.WorkerListResponse;
import com.bookstudio.worker.application.dto.response.WorkerFilterOptionsResponse;
import com.bookstudio.worker.domain.model.Worker;
import com.bookstudio.worker.domain.model.type.WorkerStatus;
import com.bookstudio.worker.infrastructure.repository.WorkerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final RoleApi roleApi;

    public List<WorkerListResponse> getList(Long loggedId) {
        return workerRepository.findList(loggedId);
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
            throw new IllegalArgumentException("The provided username is already registered.");
        }

        if (workerRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("The provided email address is already registered.");
        }

        Worker worker = new Worker();
        roleApi.requireExists(request.roleId());
        worker.setRoleId(request.roleId());

        worker.setUsername(request.username());
        worker.setEmail(request.email());
        worker.setFirstName(request.firstName());
        worker.setLastName(request.lastName());
        worker.setPassword(request.password());
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
