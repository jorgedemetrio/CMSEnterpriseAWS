package com.newsflow.users.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsflow.users.api.mapper.UserMapper;
import com.newsflow.users.domain.RoleEntity;
import com.newsflow.users.domain.UserEntity;
import com.newsflow.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class UserControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    void shouldListUsers() throws Exception {
        UserEntity user = buildUser("Jorge", "jorge@example.com", "ADMIN");
        when(userService.list()).thenReturn(List.of(user));
        when(userMapper.toResponse(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity mapped = invocation.getArgument(0);
            return new UserResponse(
                    mapped.getId(),
                    mapped.getName(),
                    mapped.getEmail(),
                    mapped.getRole() != null ? mapped.getRole().getId() : null,
                    mapped.getRole() != null ? mapped.getRole().getName() : null,
                    mapped.getStatusDado()
            );
        });

        mockMvc.perform(get("/api/users/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jorge"))
                .andExpect(jsonPath("$[0].email").value("jorge@example.com"))
                .andExpect(jsonPath("$[0].roleName").value("ADMIN"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserEntity user = buildUser("Maria", "maria@example.com", "EDITOR");
        when(userService.create(any(CreateUserRequest.class))).thenReturn(user);
        when(userMapper.toResponse(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity mapped = invocation.getArgument(0);
            return new UserResponse(
                    mapped.getId(),
                    mapped.getName(),
                    mapped.getEmail(),
                    mapped.getRole() != null ? mapped.getRole().getId() : null,
                    mapped.getRole() != null ? mapped.getRole().getName() : null,
                    mapped.getStatusDado()
            );
        });

        CreateUserRequest request = new CreateUserRequest("Maria", "maria@example.com", UUID.randomUUID());

        mockMvc.perform(post("/api/users/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria"))
                .andExpect(jsonPath("$.email").value("maria@example.com"))
                .andExpect(jsonPath("$.roleName").value("EDITOR"));
    }

    @Test
    void shouldReturnBadRequestForInvalidPayload() throws Exception {
        String invalidPayload = "{\"name\":\"\",\"email\":\"invalido\",\"roleId\":null}";

        mockMvc.perform(post("/api/users/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.details").isArray())
            .andExpect(jsonPath("$.details.length()").value(3));
    }

    @Test
    void shouldReturnNotFoundOnDeleteWhenUserDoesNotExist() throws Exception {
        UUID userId = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("User not found")).when(userService).softDelete(userId);

        mockMvc.perform(delete("/api/users/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    private UserEntity buildUser(String name, String email, String roleName) {
        RoleEntity role = new RoleEntity();
        role.setName(roleName);

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        return user;
    }
}
