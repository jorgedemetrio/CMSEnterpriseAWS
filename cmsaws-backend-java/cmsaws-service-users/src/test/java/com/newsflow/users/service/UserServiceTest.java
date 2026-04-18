package com.newsflow.users.service;

import com.newsflow.users.api.CreateUserRequest;
import com.newsflow.users.api.InvalidReferenceException;
import com.newsflow.users.api.ResourceNotFoundException;
import com.newsflow.users.domain.RoleEntity;
import com.newsflow.users.domain.UserEntity;
import com.newsflow.users.repository.RoleRepository;
import com.newsflow.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldListUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new UserEntity()));

        List<UserEntity> result = userService.list();

        assertTrue(result.size() == 1);
        verify(userRepository).findAll();
    }

    @Test
    void shouldCreateUser() {
        UUID roleId = UUID.randomUUID();
        CreateUserRequest request = new CreateUserRequest("Jorge", "jorge@example.com", roleId);
        RoleEntity role = new RoleEntity();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity created = userService.create(request);

        assertTrue("Jorge".equals(created.getName()));
        assertTrue("jorge@example.com".equals(created.getEmail()));
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldFailWhenRoleDoesNotExist() {
        UUID roleId = UUID.randomUUID();
        CreateUserRequest request = new CreateUserRequest("Jorge", "jorge@example.com", roleId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(InvalidReferenceException.class, () -> userService.create(request));
    }

    @Test
    void shouldSoftDeleteUser() {
        UUID id = UUID.randomUUID();
        UserEntity user = new UserEntity();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.softDelete(id);

        assertTrue(user.getStatusDado() == 0);
        verify(userRepository).save(user);
    }

    @Test
    void shouldFailSoftDeleteForMissingUser() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.softDelete(id));
    }
}
