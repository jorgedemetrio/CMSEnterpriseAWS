package com.newsflow.users.api;

import com.newsflow.users.domain.RoleEntity;
import com.newsflow.users.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/roles")
public class RoleController {

    private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<RoleResponse> list() {
        return userService.listRoles().stream()
                .map(this::toResponse)
                .toList();
    }

    private RoleResponse toResponse(RoleEntity role) {
        return new RoleResponse(role.getId(), role.getName(), role.getStatusDado());
    }
}
