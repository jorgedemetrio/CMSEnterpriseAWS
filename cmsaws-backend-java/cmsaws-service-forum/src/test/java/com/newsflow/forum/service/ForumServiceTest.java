package com.newsflow.forum.service;

import com.newsflow.forum.api.CreateForumPostRequest;
import com.newsflow.forum.api.CreateForumTopicRequest;
import com.newsflow.forum.api.InvalidReferenceException;
import com.newsflow.forum.domain.ForumPostEntity;
import com.newsflow.forum.domain.ForumTopicEntity;
import com.newsflow.forum.kafka.ForumEventPublisher;
import com.newsflow.forum.repository.ForumPostRepository;
import com.newsflow.forum.repository.ForumTopicRepository;
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
class ForumServiceTest {

    @Mock
    private ForumTopicRepository topicRepository;

    @Mock
    private ForumPostRepository postRepository;

    @Mock
    private ForumEventPublisher eventPublisher;

    @InjectMocks
    private ForumService forumService;

    @Test
    void shouldListTopics() {
        when(topicRepository.findAll()).thenReturn(List.of(new ForumTopicEntity()));

        List<ForumTopicEntity> result = forumService.listTopics();

        assertTrue(result.size() == 1);
        verify(topicRepository).findAll();
    }

    @Test
    void shouldCreateTopic() {
        CreateForumTopicRequest request = new CreateForumTopicRequest("Tema");
        when(topicRepository.save(any(ForumTopicEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ForumTopicEntity created = forumService.createTopic(request);

        assertTrue("Tema".equals(created.getTitle()));
    }

    @Test
    void shouldListPosts() {
        when(postRepository.findAll()).thenReturn(List.of(new ForumPostEntity()));

        List<ForumPostEntity> result = forumService.listPosts();

        assertTrue(result.size() == 1);
        verify(postRepository).findAll();
    }

    @Test
    void shouldCreatePost() {
        UUID topicId = UUID.randomUUID();
        ForumTopicEntity topic = new ForumTopicEntity();
        CreateForumPostRequest request = new CreateForumPostRequest(topicId, "Ana", "Conteudo");

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(postRepository.save(any(ForumPostEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ForumPostEntity created = forumService.createPost(request);

        assertTrue("Ana".equals(created.getAuthorName()));
        assertTrue("Conteudo".equals(created.getContent()));
        assertTrue(created.getTopic() == topic);
    }

    @Test
    void shouldFailCreatePostForMissingTopic() {
        UUID topicId = UUID.randomUUID();
        CreateForumPostRequest request = new CreateForumPostRequest(topicId, "Ana", "Conteudo");
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertThrows(InvalidReferenceException.class, () -> forumService.createPost(request));
    }
}
