package com.bookstudio.language.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.language.domain.model.Language;
import com.bookstudio.shared.response.OptionResponse;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("""
        SELECT 
            l.id AS value, 
            l.name AS label
        FROM Language l 
        ORDER BY l.name ASC
    """)
    List<OptionResponse> findForOptions();
}
