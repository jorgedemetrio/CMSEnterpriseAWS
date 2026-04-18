package com.newsflow.forum.service;

import com.newsflow.forum.api.CreateForumPostRequest;
import com.newsflow.forum.api.CreateForumTopicRequest;
import com.newsflow.forum.api.InvalidReferenceException;
import com.newsflow.forum.domain.ForumPostEntity;
import com.newsflow.forum.domain.ForumTopicEntity;
import com.newsflow.forum.kafka.ForumEventPublisher;
import com.newsflow.forum.repository.ForumPostRepository;
import com.newsflow.forum.repository.ForumTopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForumService {

    private final ForumTopicRepository topicRepository;
    private final ForumPostRepository postRepository;
    private final ForumEventPublisher eventPublisher;

    public ForumService(ForumTopicRepository topicRepository, ForumPostRepository postRepository, ForumEventPublisher eventPublisher) {
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<ForumTopicEntity> listTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    public ForumTopicEntity createTopic(CreateForumTopicRequest request) {
        ForumTopicEntity topic = new ForumTopicEntity();
        topic.setTitle(request.title());
        ForumTopicEntity saved = topicRepository.save(topic);

        eventPublisher.publish(
                "forum.topic.created",
                saved.getId(),
                Map.of("title", saved.getTitle())
        );

        return saved;
    }

    @Transactional(readOnly = true)
    public List<ForumPostEntity> listPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public ForumPostEntity createPost(CreateForumPostRequest request) {
        ForumTopicEntity topic = topicRepository.findById(request.topicId())
                .orElseThrow(() -> new InvalidReferenceException("Topic not found"));

        ForumPostEntity post = new ForumPostEntity();
        post.setTopic(topic);
        post.setAuthorName(request.authorName());
        post.setContent(request.content());

        ForumPostEntity saved = postRepository.save(post);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("topicId", request.topicId());
        payload.put("authorName", request.authorName());
        payload.put("content", request.content());

        eventPublisher.publish(
            "forum.post.created",
            saved.getId(),
            payload
        );

        return saved;
    }
}
