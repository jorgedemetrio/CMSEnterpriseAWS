package com.newsflow.core.service;

import com.newsflow.core.api.CreateArticleRequest;
import com.newsflow.core.api.CreateCategoryRequest;
import com.newsflow.core.api.InvalidReferenceException;
import com.newsflow.core.domain.ArticleEntity;
import com.newsflow.core.domain.CategoryEntity;
import com.newsflow.core.repository.ArticleRepository;
import com.newsflow.core.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        return articleRepository.save(article);
    }
}
