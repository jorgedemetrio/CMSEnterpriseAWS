package com.newsflow.forum.api;

import com.newsflow.forum.api.mapper.ForumTopicMapper;
import com.newsflow.forum.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/topics")
public class ForumTopicController {

    private final ForumService forumService;
    private final ForumTopicMapper forumTopicMapper;

    public ForumTopicController(ForumService forumService, ForumTopicMapper forumTopicMapper) {
        this.forumService = forumService;
        this.forumTopicMapper = forumTopicMapper;
    }

    @GetMapping
    public List<ForumTopicResponse> list() {
        return forumService.listTopics().stream()
                .map(forumTopicMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ForumTopicResponse create(@Valid @RequestBody CreateForumTopicRequest request) {
        return forumTopicMapper.toResponse(forumService.createTopic(request));
    }
}