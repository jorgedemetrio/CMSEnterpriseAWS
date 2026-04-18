package com.newsflow.forum.api;

import com.newsflow.forum.api.mapper.ForumPostMapper;
import com.newsflow.forum.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/posts")
public class ForumPostController {

    private final ForumService forumService;
    private final ForumPostMapper forumPostMapper;

    public ForumPostController(ForumService forumService, ForumPostMapper forumPostMapper) {
        this.forumService = forumService;
        this.forumPostMapper = forumPostMapper;
    }

    @GetMapping
    public List<ForumPostResponse> list() {
        return forumService.listPosts().stream()
                .map(forumPostMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ForumPostResponse create(@Valid @RequestBody CreateForumPostRequest request) {
        return forumPostMapper.toResponse(forumService.createPost(request));
    }
}