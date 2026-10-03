package com.bookstudio.catalog.nationality.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.catalog.nationality.NationalityApi;
import com.bookstudio.catalog.nationality.infrastructure.repository.NationalityRepository;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.response.OptionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NationalityService implements NationalityApi {
    private final NationalityRepository nationalityRepository;

    @Override
    public void requireExists(Long id) {
        if (!nationalityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nationality not found with ID: " + id);
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return nationalityRepository.findForOptions();
    }
}
