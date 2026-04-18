package com.newsflow.forum.api;

import com.newsflow.forum.domain.ForumPostEntity;
import com.newsflow.forum.service.ForumService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/posts")
public class ForumPostController {

    private final ForumService forumService;

    public ForumPostController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping
    public List<ForumPostResponse> list() {
        return forumService.listPosts().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public ForumPostResponse create(@Valid @RequestBody CreateForumPostRequest request) {
        return toResponse(forumService.createPost(request));
    }

    private ForumPostResponse toResponse(ForumPostEntity post) {
        return new ForumPostResponse(
                post.getId(),
                post.getTopic().getId(),
                post.getTopic().getTitle(),
                post.getAuthorName(),
                post.getContent()
        );
    }
}