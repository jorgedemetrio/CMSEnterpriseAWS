package com.newsflow.users.api;

import com.newsflow.users.api.mapper.RoleMapper;
import com.newsflow.users.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/roles")
public class RoleController {

    private final UserService userService;
    private final RoleMapper roleMapper;

    public RoleController(UserService userService, RoleMapper roleMapper) {
        this.userService = userService;
        this.roleMapper = roleMapper;
    }

    @GetMapping
    public List<RoleResponse> list() {
        return userService.listRoles().stream()
                .map(roleMapper::toResponse)
                .toList();
    }
}
