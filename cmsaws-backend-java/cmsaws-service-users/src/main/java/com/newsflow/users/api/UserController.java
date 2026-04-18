package com.newsflow.users.api;

import com.newsflow.users.api.mapper.UserMapper;
import com.newsflow.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.list().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return userMapper.toResponse(userService.create(request));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @Valid @RequestBody CreateUserRequest request) {
        return userMapper.toResponse(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable UUID id) {
        userService.softDelete(id);
    }
}