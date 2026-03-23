package com.bookstudio.reader.application;

import com.bookstudio.reader.application.dto.request.CreateReaderRequest;
import com.bookstudio.reader.application.dto.request.UpdateReaderRequest;
import com.bookstudio.reader.application.dto.response.ReaderDetailResponse;
import com.bookstudio.reader.application.dto.response.ReaderListResponse;
import com.bookstudio.reader.domain.model.Reader;
import com.bookstudio.reader.domain.model.type.ReaderGender;
import com.bookstudio.reader.domain.model.type.ReaderStatus;
import com.bookstudio.reader.domain.model.type.ReaderType;
import com.bookstudio.reader.infrastructure.repository.ReaderRepository;
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
public class ReaderService {
    @PersistenceContext
    private EntityManager entityManager;

    private final ReaderRepository readerRepository;

    public List<ReaderListResponse> getList() {
        return readerRepository.findList();
    }

    public ReaderDetailResponse getDetailById(Long id) {
        return readerRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader not found with ID: " + id));
    }

    @Transactional
    public ReaderListResponse create(CreateReaderRequest request) {
        if (readerRepository.findByDni(request.dni()).isPresent()) {
            throw new IllegalArgumentException("The provided DNI is already registered.");
        }

        if (readerRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("The provided email address is already registered.");
        }

        Reader reader = new Reader();
        reader.setDni(request.dni());
        reader.setFirstName(request.firstName());
        reader.setLastName(request.lastName());
        reader.setAddress(request.address());
        reader.setPhone(request.phone());
        reader.setEmail(request.email());
        reader.setBirthDate(request.birthDate());
        reader.setGender(ReaderGender.valueOf(request.gender()));
        reader.setType(ReaderType.valueOf(request.type()));
        reader.setStatus(ReaderStatus.valueOf(request.status()));

        Reader saved = readerRepository.save(reader);
        entityManager.refresh(saved);

        return toListResponse(saved);
    }

    @Transactional
    public ReaderListResponse update(Long id, UpdateReaderRequest request) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader not found with ID: " + id));

        if (readerRepository.findByEmailAndIdNot(request.email(), id).isPresent()) {
            throw new IllegalArgumentException("The provided email address is already registered.");
        }

        reader.setFirstName(request.firstName());
        reader.setLastName(request.lastName());
        reader.setAddress(request.address());
        reader.setPhone(request.phone());
        reader.setEmail(request.email());
        reader.setBirthDate(request.birthDate());
        reader.setGender(ReaderGender.valueOf(request.gender()));
        reader.setType(ReaderType.valueOf(request.type()));
        reader.setStatus(ReaderStatus.valueOf(request.status()));

        Reader updated = readerRepository.save(reader);
        return toListResponse(updated);
    }

    private ReaderListResponse toListResponse(Reader reader) {
        return new ReaderListResponse(
                reader.getId(),
                reader.getCode(),
                reader.getFullName(),
                reader.getPhone(),
                reader.getEmail(),
                reader.getType(),
                reader.getStatus());
    }
}
