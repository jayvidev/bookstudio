package com.bookstudio.category.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstudio.category.application.dto.response.CategoryDetailResponse;
import com.bookstudio.category.application.dto.response.CategoryListResponse;
import com.bookstudio.category.domain.model.Category;
import com.bookstudio.shared.response.OptionResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
        SELECT 
            c.id AS id,
            c.name AS name,
            c.level AS level,
            c.description AS description,
            c.status AS status
        FROM Category c
        ORDER BY c.id DESC
    """)
    List<CategoryListResponse> findList();

    @Query("""
        SELECT 
            c.id AS value,
            c.name AS label
        FROM Category c
        WHERE c.status = 'ACTIVO'
        ORDER BY c.name ASC
    """)
    List<OptionResponse> findForOptions();

    @Query("""
        SELECT 
            c.id AS id,
            c.name AS name,
            c.level AS level,
            c.description AS description,
            c.status AS status
        FROM Category c
        WHERE c.id = :id
    """)
    Optional<CategoryDetailResponse> findDetailById(Long id);
}
