package com.newsflow.core.api;

import com.newsflow.core.domain.CategoryEntity;
import com.newsflow.core.service.CoreCatalogService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/core/categories")
public class CategoryController {

    private final CoreCatalogService coreCatalogService;

    public CategoryController(CoreCatalogService coreCatalogService) {
        this.coreCatalogService = coreCatalogService;
    }

    @GetMapping
    public List<CategoryResponse> list() {
        return coreCatalogService.listCategories().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        return toResponse(coreCatalogService.createCategory(request));
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable UUID id, @Valid @RequestBody CreateCategoryRequest request) {
        return toResponse(coreCatalogService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable UUID id) {
        coreCatalogService.softDeleteCategory(id);
    }

    private CategoryResponse toResponse(CategoryEntity category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}