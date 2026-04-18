package com.newsflow.users.service;

import com.newsflow.users.api.CreateUserRequest;
import com.newsflow.users.api.InvalidReferenceException;
import com.newsflow.users.api.ResourceNotFoundException;
import com.newsflow.users.domain.RoleEntity;
import com.newsflow.users.domain.UserEntity;
import com.newsflow.users.repository.RoleRepository;
import com.newsflow.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<UserEntity> list() {
        return userRepository.findAll();
    }

    @Transactional
    public UserEntity create(CreateUserRequest request) {
        RoleEntity role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new InvalidReferenceException("Role not found"));

        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(role);

        return userRepository.save(user);
    }

    @Transactional
    public void softDelete(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatusDado(0);
        userRepository.save(user);
    }
}
