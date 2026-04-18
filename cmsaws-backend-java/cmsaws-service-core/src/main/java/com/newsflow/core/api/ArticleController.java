package com.newsflow.core.api;

import com.newsflow.core.domain.ArticleEntity;
import com.newsflow.core.service.CoreCatalogService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/core/articles")
public class ArticleController {

    private final CoreCatalogService coreCatalogService;

    public ArticleController(CoreCatalogService coreCatalogService) {
        this.coreCatalogService = coreCatalogService;
    }

    @GetMapping
    public List<ArticleResponse> list() {
        return coreCatalogService.listArticles().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public ArticleResponse create(@Valid @RequestBody CreateArticleRequest request) {
        return toResponse(coreCatalogService.createArticle(request));
    }

    @PutMapping("/{id}")
    public ArticleResponse update(@PathVariable UUID id, @Valid @RequestBody CreateArticleRequest request) {
        return toResponse(coreCatalogService.updateArticle(id, request));
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable UUID id) {
        coreCatalogService.softDeleteArticle(id);
    }

    private ArticleResponse toResponse(ArticleEntity article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCategory().getId(),
                article.getCategory().getName(),
                article.isHighlight()
        );
    }
}