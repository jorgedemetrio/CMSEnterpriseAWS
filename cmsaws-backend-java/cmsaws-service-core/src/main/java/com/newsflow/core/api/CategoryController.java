package com.newsflow.core.api;

import com.newsflow.core.api.mapper.CategoryMapper;
import com.newsflow.core.service.CoreCatalogService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/core/categories")
public class CategoryController {

    private final CoreCatalogService coreCatalogService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CoreCatalogService coreCatalogService, CategoryMapper categoryMapper) {
        this.coreCatalogService = coreCatalogService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryResponse> list() {
        return coreCatalogService.listCategories().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryMapper.toResponse(coreCatalogService.createCategory(request));
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable UUID id, @Valid @RequestBody CreateCategoryRequest request) {
        return categoryMapper.toResponse(coreCatalogService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable UUID id) {
        coreCatalogService.softDeleteCategory(id);
    }
}