package com.bookstudio.nationality.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.nationality.domain.model.Nationality;
import com.bookstudio.shared.response.OptionResponse;

public interface NationalityRepository extends JpaRepository<Nationality, Long> {
    @Query("""
        SELECT 
            n.id AS value,
            n.name AS label
        FROM Nationality n 
        ORDER BY n.name ASC
    """)
    List<OptionResponse> findForOptions();
}
