package com.newsflow.core.api;

import com.newsflow.core.api.mapper.ArticleMapper;
import com.newsflow.core.service.CoreCatalogService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/core/articles")
public class ArticleController {

    private final CoreCatalogService coreCatalogService;
    private final ArticleMapper articleMapper;

    public ArticleController(CoreCatalogService coreCatalogService, ArticleMapper articleMapper) {
        this.coreCatalogService = coreCatalogService;
        this.articleMapper = articleMapper;
    }

    @GetMapping
    public List<ArticleResponse> list() {
        return coreCatalogService.listArticles().stream()
                .map(articleMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ArticleResponse create(@Valid @RequestBody CreateArticleRequest request) {
        return articleMapper.toResponse(coreCatalogService.createArticle(request));
    }

    @PutMapping("/{id}")
    public ArticleResponse update(@PathVariable UUID id, @Valid @RequestBody CreateArticleRequest request) {
        return articleMapper.toResponse(coreCatalogService.updateArticle(id, request));
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable UUID id) {
        coreCatalogService.softDeleteArticle(id);
    }
}