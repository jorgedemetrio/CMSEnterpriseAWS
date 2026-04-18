package com.newsflow.forum.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsflow.forum.api.mapper.ForumPostMapper;
import com.newsflow.forum.api.mapper.ForumTopicMapper;
import com.newsflow.forum.domain.ForumPostEntity;
import com.newsflow.forum.domain.ForumTopicEntity;
import com.newsflow.forum.service.ForumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@WebMvcTest({ForumTopicController.class, ForumPostController.class})
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ForumControllersWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ForumService forumService;

    @MockBean
    private ForumTopicMapper forumTopicMapper;

    @MockBean
    private ForumPostMapper forumPostMapper;

    @Test
    void shouldListTopics() throws Exception {
        ForumTopicEntity topic = new ForumTopicEntity();
        topic.setTitle("Backend");

        when(forumService.listTopics()).thenReturn(List.of(topic));
        when(forumTopicMapper.toResponse(any(ForumTopicEntity.class))).thenAnswer(invocation -> {
            ForumTopicEntity mapped = invocation.getArgument(0);
            return new ForumTopicResponse(mapped.getId(), mapped.getTitle());
        });

        mockMvc.perform(get("/api/forum/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Backend"));
    }

    @Test
    void shouldCreateTopic() throws Exception {
        ForumTopicEntity topic = new ForumTopicEntity();
        topic.setTitle("Cloud");

        when(forumService.createTopic(any(CreateForumTopicRequest.class))).thenReturn(topic);
        when(forumTopicMapper.toResponse(any(ForumTopicEntity.class))).thenAnswer(invocation -> {
            ForumTopicEntity mapped = invocation.getArgument(0);
            return new ForumTopicResponse(mapped.getId(), mapped.getTitle());
        });

        CreateForumTopicRequest request = new CreateForumTopicRequest("Cloud");

        mockMvc.perform(post("/api/forum/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Cloud"));
    }

    @Test
    void shouldListPosts() throws Exception {
        ForumTopicEntity topic = new ForumTopicEntity();
        topic.setTitle("Arquitetura");

        ForumPostEntity post = new ForumPostEntity();
        post.setTopic(topic);
        post.setAuthorName("Rafa");
        post.setContent("Bom ponto");

        when(forumService.listPosts()).thenReturn(List.of(post));
        when(forumPostMapper.toResponse(any(ForumPostEntity.class))).thenAnswer(invocation -> {
            ForumPostEntity mapped = invocation.getArgument(0);
            return new ForumPostResponse(
                mapped.getId(),
                mapped.getTopic() != null ? mapped.getTopic().getId() : null,
                mapped.getTopic() != null ? mapped.getTopic().getTitle() : null,
                mapped.getAuthorName(),
                mapped.getContent()
            );
        });

        mockMvc.perform(get("/api/forum/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].authorName").value("Rafa"))
                .andExpect(jsonPath("$[0].topicTitle").value("Arquitetura"));
    }

    @Test
    void shouldCreatePost() throws Exception {
        ForumTopicEntity topic = new ForumTopicEntity();
        topic.setTitle("Java");

        ForumPostEntity post = new ForumPostEntity();
        post.setTopic(topic);
        post.setAuthorName("Lia");
        post.setContent("Excelente");

        when(forumService.createPost(any(CreateForumPostRequest.class))).thenReturn(post);
        when(forumPostMapper.toResponse(any(ForumPostEntity.class))).thenAnswer(invocation -> {
            ForumPostEntity mapped = invocation.getArgument(0);
            return new ForumPostResponse(
                mapped.getId(),
                mapped.getTopic() != null ? mapped.getTopic().getId() : null,
                mapped.getTopic() != null ? mapped.getTopic().getTitle() : null,
                mapped.getAuthorName(),
                mapped.getContent()
            );
        });

        CreateForumPostRequest request = new CreateForumPostRequest(UUID.randomUUID(), "Lia", "Excelente");

        mockMvc.perform(post("/api/forum/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value("Lia"))
                .andExpect(jsonPath("$.topicTitle").value("Java"));
    }

    @Test
    void shouldReturnValidationDetailsForInvalidPostPayload() throws Exception {
        String invalidPayload = "{\"topicId\":null,\"authorName\":\"\",\"content\":\"\"}";

        mockMvc.perform(post("/api/forum/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details.length()").value(3));
    }
}
