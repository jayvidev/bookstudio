package com.bookstudio.catalog.language.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstudio.catalog.language.LanguageApi;
import com.bookstudio.catalog.language.infrastructure.repository.LanguageRepository;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.response.OptionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LanguageService implements LanguageApi {
    private final LanguageRepository languageRepository;

    @Override
    public void requireExists(Long id) {
        if (!languageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Language not found with ID: " + id);
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return languageRepository.findForOptions();
    }
}
