package com.bookstudio.catalog.category.application;

import com.bookstudio.catalog.category.CategoryApi;
import com.bookstudio.catalog.category.application.dto.request.CategoryFilter;
import com.bookstudio.catalog.category.application.dto.request.CreateCategoryRequest;
import com.bookstudio.catalog.category.application.dto.request.UpdateCategoryRequest;
import com.bookstudio.catalog.category.application.dto.response.CategoryDetailResponse;
import com.bookstudio.catalog.category.application.dto.response.CategoryListResponse;
import com.bookstudio.catalog.category.domain.model.Category;
import com.bookstudio.catalog.category.domain.model.type.CategoryLevel;
import com.bookstudio.catalog.category.infrastructure.repository.CategoryRepository;
import com.bookstudio.shared.api.PageResponse;
import com.bookstudio.shared.exception.ResourceNotFoundException;
import com.bookstudio.shared.paging.PageProjection;
import com.bookstudio.shared.paging.SortWhitelist;
import com.bookstudio.shared.paging.Specs;
import com.bookstudio.shared.response.OptionResponse;
import com.bookstudio.shared.type.Status;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CategoryService implements CategoryApi {
    private static final SortWhitelist SORTABLE = SortWhitelist.of("id", "name");

    private final CategoryRepository categoryRepository;

    @Override
    public void requireExists(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with ID: " + id);
        }
    }

    @Override
    public List<OptionResponse> getOptions() {
        return categoryRepository.findForOptions();
    }

    public PageResponse<CategoryListResponse> getPage(CategoryFilter filter, Pageable pageable) {
        Specification<Category> spec = Specification.allOf(
                Specs.containsIgnoreCase(filter.search(), "name"),
                Specs.equal("level", filter.level()),
                Specs.equal("status", filter.status()));

        return PageProjection.of(
                categoryRepository.findAll(spec, SORTABLE.validate(pageable)),
                Category::getId,
                categoryRepository::findListByIds,
                CategoryListResponse::id);
    }

    public CategoryDetailResponse getDetailById(Long id) {
        return categoryRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
    }

    @Transactional
    public CategoryListResponse create(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setLevel(CategoryLevel.valueOf(request.level()));
        category.setDescription(request.description());
        category.setStatus(Status.valueOf(request.status()));

        Category saved = categoryRepository.save(category);

        return toListResponse(saved);
    }

    @Transactional
    public CategoryListResponse update(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        category.setName(request.name());
        category.setLevel(CategoryLevel.valueOf(request.level()));
        category.setDescription(request.description());
        category.setStatus(Status.valueOf(request.status()));

        Category updated = categoryRepository.save(category);

        return toListResponse(updated);
    }

    private CategoryListResponse toListResponse(Category category) {
        return new CategoryListResponse(
                category.getId(),
                category.getName(),
                category.getLevel(),
                category.getDescription(),
                category.getStatus());
    }
}
