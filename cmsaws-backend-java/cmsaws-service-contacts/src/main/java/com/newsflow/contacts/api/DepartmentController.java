package com.newsflow.contacts.api;

import com.newsflow.contacts.domain.DepartmentEntity;
import com.newsflow.contacts.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts/departments")
public class DepartmentController {

    private final ContactService contactService;

    public DepartmentController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<DepartmentResponse> list() {
        return contactService.listDepartments().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public DepartmentResponse create(@Valid @RequestBody CreateDepartmentRequest request) {
        return toResponse(contactService.createDepartment(request));
    }

    private DepartmentResponse toResponse(DepartmentEntity department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName()
        );
    }
}