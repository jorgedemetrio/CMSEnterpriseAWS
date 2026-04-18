package com.newsflow.forum.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/forum")
public class ApiIndexController {

    @GetMapping
    public RepresentationModel<?> index() {
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(ForumTopicController.class).list()).withRel("topics"));
        model.add(linkTo(methodOn(ForumPostController.class).list()).withRel("posts"));
        return model;
    }
}
