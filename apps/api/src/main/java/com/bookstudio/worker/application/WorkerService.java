package com.bookstudio.worker.application;

import com.bookstudio.role.infrastructure.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    public List<WorkerListResponse> getList(Long loggedId) {
        return workerRepository.findList(loggedId);
    }

    public WorkerFilterOptionsResponse getFilterOptions() {
        return new WorkerFilterOptionsResponse(roleRepository.findForOptions());
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
        worker.setRole(roleRepository.findById(request.roleId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Role not found with ID: " + request.roleId())));

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

        worker.setRole(roleRepository.findById(request.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + request.roleId())));

        worker.setFirstName(request.firstName());
        worker.setLastName(request.lastName());
        worker.setProfilePhotoUrl(request.profilePhotoUrl());
        worker.setStatus(WorkerStatus.valueOf(request.status()));

        Worker updated = workerRepository.save(worker);
        return toListResponse(updated);
    }

    private WorkerListResponse toListResponse(Worker worker) {
        return new WorkerListResponse(
                worker.getId(),
                worker.getProfilePhotoUrl(),
                worker.getUsername(),
                worker.getEmail(),
                worker.getFullName(),

                worker.getRole().getId(),
                worker.getRole().getName(),

                worker.getStatus());
    }
}
