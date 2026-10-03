package com.bookstudio.membership.reader.application;

import com.bookstudio.membership.ReaderApi;
import com.bookstudio.membership.reader.application.dto.request.CreateReaderRequest;
import com.bookstudio.membership.reader.application.dto.request.ReaderFilter;
import com.bookstudio.membership.reader.application.dto.request.UpdateReaderRequest;
import com.bookstudio.membership.reader.application.dto.response.ReaderDetailResponse;
import com.bookstudio.membership.reader.application.dto.response.ReaderListResponse;
import com.bookstudio.membership.reader.domain.model.Reader;
import com.bookstudio.membership.reader.domain.model.type.ReaderGender;
import com.bookstudio.membership.reader.domain.model.type.ReaderStatus;
import com.bookstudio.membership.reader.domain.model.type.ReaderType;
import com.bookstudio.membership.reader.infrastructure.repository.ReaderRepository;
import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.code.CodeGenerator;
import com.bookstudio.shared.exception.BusinessRuleException;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.PageProjection;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;
import com.bookstudio.shared.response.OptionResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class ReaderService implements ReaderApi {
    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "code", "lastName");

    private final CodeGenerator codeGenerator;

    private final ReaderRepository readerRepository;

    @Override
    public void requireExists(Long id) {
        if (!readerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reader not found with ID: " + id);
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return readerRepository.findForOptions();
    }

    @Override
    public List<Long> findIdsByName(String text) {
        return readerRepository.findIdsByFullNameContaining(text);
    }

    public PageResponse<ReaderListResponse> getPage(ReaderFilter filter, Pageable pageable) {
        Specification<Reader> spec = Specification.allOf(
                filter.search() == null || filter.search().isBlank()
                        ? Specification.unrestricted()
                        : Specification.anyOf(
                                Specs.containsIgnoreCase(filter.search(), "code", "dni", "email"),
                                Specs.fullNameContains(filter.search(), "firstName", "lastName")),
                Specs.equal("type", filter.type()),
                Specs.equal("status", filter.status()));

        return PageProjection.of(
                readerRepository.findAll(spec, SORTABLE.validate(pageable)),
                Reader::getId,
                readerRepository::findListByIds,
                ReaderListResponse::id);
    }

    public ReaderDetailResponse getDetailById(Long id) {
        return readerRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader not found with ID: " + id));
    }

    @Transactional
    public ReaderListResponse create(CreateReaderRequest request) {
        if (readerRepository.findByDni(request.dni()).isPresent()) {
            throw new BusinessRuleException("The provided DNI is already registered.");
        }

        if (readerRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessRuleException("The provided email address is already registered.");
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

        reader.setCode(codeGenerator.next(Reader.CODE_SERIES, LocalDate.now()));

        Reader saved = readerRepository.save(reader);

        return toListResponse(saved);
    }

    @Transactional
    public ReaderListResponse update(Long id, UpdateReaderRequest request) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader not found with ID: " + id));

        if (readerRepository.findByEmailAndIdNot(request.email(), id).isPresent()) {
            throw new BusinessRuleException("The provided email address is already registered.");
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
