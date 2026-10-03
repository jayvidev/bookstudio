package com.bookstudio.catalog.category.infrastructure.repository;

import com.bookstudio.catalog.category.application.dto.response.CategoryDetailResponse;
import com.bookstudio.catalog.category.application.dto.response.CategoryListResponse;
import com.bookstudio.catalog.category.domain.model.Category;
import com.bookstudio.shared.response.OptionResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    String LIST_SELECT = """
        SELECT 
            c.id AS id,
            c.name AS name,
            c.level AS level,
            c.description AS description,
            c.status AS status
        FROM Category c
        """;

    @Query(LIST_SELECT + "WHERE c.id IN :ids ")
    List<CategoryListResponse> findListByIds(Collection<Long> ids);

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
