package com.newsflow.core.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsflow.core.domain.ArticleEntity;
import com.newsflow.core.domain.CategoryEntity;
import com.newsflow.core.service.CoreCatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CategoryController.class, ArticleController.class})
@Import(GlobalExceptionHandler.class)
class CoreControllersWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoreCatalogService coreCatalogService;

    @Test
    void shouldListCategories() throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setName("Tecnologia");
        when(coreCatalogService.listCategories()).thenReturn(List.of(category));

        mockMvc.perform(get("/api/core/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tecnologia"));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setName("Noticias");
        when(coreCatalogService.createCategory(any(CreateCategoryRequest.class))).thenReturn(category);

        CreateCategoryRequest request = new CreateCategoryRequest("Noticias");

        mockMvc.perform(post("/api/core/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Noticias"));
    }

    @Test
    void shouldListArticles() throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setName("Tecnologia");

        ArticleEntity article = new ArticleEntity();
        article.setTitle("Novo release");
        article.setContent("Conteudo da materia");
        article.setCategory(category);

        when(coreCatalogService.listArticles()).thenReturn(List.of(article));

        mockMvc.perform(get("/api/core/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Novo release"))
                .andExpect(jsonPath("$[0].categoryName").value("Tecnologia"));
    }

    @Test
    void shouldCreateArticle() throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setName("Mercado");

        ArticleEntity article = new ArticleEntity();
        article.setTitle("Materia");
        article.setContent("Texto");
        article.setCategory(category);

        when(coreCatalogService.createArticle(any(CreateArticleRequest.class))).thenReturn(article);

        CreateArticleRequest request = new CreateArticleRequest("Materia", "Texto", UUID.randomUUID(), true);

        mockMvc.perform(post("/api/core/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Materia"))
                .andExpect(jsonPath("$.categoryName").value("Mercado"));
    }

    @Test
    void shouldReturnValidationDetailsForInvalidArticlePayload() throws Exception {
        String invalidPayload = "{\"title\":\"\",\"content\":\"\",\"categoryId\":null}";

        mockMvc.perform(post("/api/core/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details.length()").value(3));
    }
}
