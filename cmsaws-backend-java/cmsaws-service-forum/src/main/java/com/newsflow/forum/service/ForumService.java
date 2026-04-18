package com.newsflow.forum.service;

import com.newsflow.forum.api.CreateForumPostRequest;
import com.newsflow.forum.api.CreateForumTopicRequest;
import com.newsflow.forum.api.InvalidReferenceException;
import com.newsflow.forum.domain.ForumPostEntity;
import com.newsflow.forum.domain.ForumTopicEntity;
import com.newsflow.forum.repository.ForumPostRepository;
import com.newsflow.forum.repository.ForumTopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumService {

    private final ForumTopicRepository topicRepository;
    private final ForumPostRepository postRepository;

    public ForumService(ForumTopicRepository topicRepository, ForumPostRepository postRepository) {
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<ForumTopicEntity> listTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    public ForumTopicEntity createTopic(CreateForumTopicRequest request) {
        ForumTopicEntity topic = new ForumTopicEntity();
        topic.setTitle(request.title());
        return topicRepository.save(topic);
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

        return postRepository.save(post);
    }
}
