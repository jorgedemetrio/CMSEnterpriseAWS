package com.newsflow.contacts.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/contacts")
public class ApiIndexController {

    @GetMapping
    public RepresentationModel<?> index() {
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(DepartmentController.class).list()).withRel("departments"));
        model.add(linkTo(methodOn(ContactMessageController.class).list()).withRel("messages"));
        return model;
    }
}
