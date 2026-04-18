package com.newsflow.core.service;

import com.newsflow.core.api.CreateArticleRequest;
import com.newsflow.core.api.CreateCategoryRequest;
import com.newsflow.core.api.InvalidReferenceException;
import com.newsflow.core.api.ResourceNotFoundException;
import com.newsflow.core.domain.ArticleEntity;
import com.newsflow.core.domain.CategoryEntity;
import com.newsflow.core.repository.ArticleRepository;
import com.newsflow.core.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CoreCatalogService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public CoreCatalogService(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> listCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public CategoryEntity createCategory(CreateCategoryRequest request) {
        CategoryEntity category = new CategoryEntity();
        category.setName(request.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public CategoryEntity updateCategory(UUID id, CreateCategoryRequest request) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(request.name());
        return categoryRepository.save(category);
    }

    @Transactional
    public void softDeleteCategory(UUID id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setStatusDado(0);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<ArticleEntity> listArticles() {
        return articleRepository.findAll();
    }

    @Transactional
    public ArticleEntity createArticle(CreateArticleRequest request) {
        CategoryEntity category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new InvalidReferenceException("Category not found"));

        ArticleEntity article = new ArticleEntity();
        article.setTitle(request.title());
        article.setContent(request.content());
        article.setCategory(category);
        article.setHighlight(request.isHighlight());

        return articleRepository.save(article);
        }

        @Transactional
        public ArticleEntity updateArticle(UUID id, CreateArticleRequest request) {
        ArticleEntity article = articleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        CategoryEntity category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new InvalidReferenceException("Category not found"));

        article.setTitle(request.title());
        article.setContent(request.content());
        article.setCategory(category);
        article.setHighlight(request.isHighlight());

        return articleRepository.save(article);
        }

        @Transactional
        public void softDeleteArticle(UUID id) {
        ArticleEntity article = articleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        article.setStatusDado(0);

        return articleRepository.save(article);
    }
}
