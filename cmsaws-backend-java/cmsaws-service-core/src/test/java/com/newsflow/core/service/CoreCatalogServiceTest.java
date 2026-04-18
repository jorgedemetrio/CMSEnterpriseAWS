package com.newsflow.core.service;

import com.newsflow.core.api.CreateArticleRequest;
import com.newsflow.core.api.CreateCategoryRequest;
import com.newsflow.core.api.InvalidReferenceException;
import com.newsflow.core.domain.ArticleEntity;
import com.newsflow.core.domain.CategoryEntity;
import com.newsflow.core.repository.ArticleRepository;
import com.newsflow.core.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoreCatalogServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private CoreCatalogService coreCatalogService;

    @Test
    void shouldListCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(new CategoryEntity()));

        List<CategoryEntity> result = coreCatalogService.listCategories();

        assertTrue(result.size() == 1);
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldCreateCategory() {
        CreateCategoryRequest request = new CreateCategoryRequest("Tecnologia");
        when(categoryRepository.save(any(CategoryEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoryEntity created = coreCatalogService.createCategory(request);

        assertTrue("Tecnologia".equals(created.getName()));
    }

    @Test
    void shouldListArticles() {
        when(articleRepository.findAll()).thenReturn(List.of(new ArticleEntity()));

        List<ArticleEntity> result = coreCatalogService.listArticles();

        assertTrue(result.size() == 1);
        verify(articleRepository).findAll();
    }

    @Test
    void shouldCreateArticle() {
        UUID categoryId = UUID.randomUUID();
        CategoryEntity category = new CategoryEntity();
        CreateArticleRequest request = new CreateArticleRequest("Titulo", "Conteudo", categoryId, true);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(articleRepository.save(any(ArticleEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArticleEntity created = coreCatalogService.createArticle(request);

        assertTrue("Titulo".equals(created.getTitle()));
        assertTrue("Conteudo".equals(created.getContent()));
        assertTrue(created.getCategory() == category);
        assertTrue(created.isHighlight());
    }

    @Test
    void shouldFailCreateArticleForMissingCategory() {
        UUID categoryId = UUID.randomUUID();
        CreateArticleRequest request = new CreateArticleRequest("Titulo", "Conteudo", categoryId, false);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(InvalidReferenceException.class, () -> coreCatalogService.createArticle(request));
    }
}
