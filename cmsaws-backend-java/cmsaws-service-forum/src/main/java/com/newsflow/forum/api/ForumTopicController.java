package com.newsflow.forum.api;

import com.newsflow.forum.domain.ForumTopicEntity;
import com.newsflow.forum.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/topics")
public class ForumTopicController {

    private final ForumService forumService;

    public ForumTopicController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping
    public List<ForumTopicResponse> list() {
        return forumService.listTopics().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public ForumTopicResponse create(@Valid @RequestBody CreateForumTopicRequest request) {
        return toResponse(forumService.createTopic(request));
    }

    private ForumTopicResponse toResponse(ForumTopicEntity topic) {
        return new ForumTopicResponse(
                topic.getId(),
                topic.getTitle()
        );
    }
}