package com.newsflow.users.api;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class ApiIndexController {

    @GetMapping
    public RepresentationModel<?> index() {
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(UserController.class).list()).withRel("users"));
        model.add(linkTo(methodOn(RoleController.class).list()).withRel("roles"));
        model.add(linkTo(methodOn(IntegrationController.class).coreHealth()).withRel("coreHealth"));
        return model;
    }
}
