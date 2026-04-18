package com.newsflow.contacts.api;

import com.newsflow.contacts.api.mapper.DepartmentMapper;
import com.newsflow.contacts.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts/departments")
public class DepartmentController {

    private final ContactService contactService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(ContactService contactService, DepartmentMapper departmentMapper) {
        this.contactService = contactService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping
    public List<DepartmentResponse> list() {
        return contactService.listDepartments().stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @PostMapping
    public DepartmentResponse create(@Valid @RequestBody CreateDepartmentRequest request) {
        return departmentMapper.toResponse(contactService.createDepartment(request));
    }
}