package com.newsflow.core.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/core")
public class ApiIndexController {

    @GetMapping
    public RepresentationModel<?> index() {
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(ArticleController.class).list()).withRel("articles"));
        model.add(linkTo(methodOn(CategoryController.class).list()).withRel("categories"));
        return model;
    }
}
