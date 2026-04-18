package com.newsflow.contacts.service;

import com.newsflow.contacts.api.CreateContactMessageRequest;
import com.newsflow.contacts.api.CreateDepartmentRequest;
import com.newsflow.contacts.api.InvalidReferenceException;
import com.newsflow.contacts.domain.ContactMessageEntity;
import com.newsflow.contacts.domain.DepartmentEntity;
import com.newsflow.contacts.repository.ContactMessageRepository;
import com.newsflow.contacts.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    private final DepartmentRepository departmentRepository;
    private final ContactMessageRepository contactMessageRepository;

    public ContactService(DepartmentRepository departmentRepository, ContactMessageRepository contactMessageRepository) {
        this.departmentRepository = departmentRepository;
        this.contactMessageRepository = contactMessageRepository;
    }

    @Transactional(readOnly = true)
    public List<DepartmentEntity> listDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public DepartmentEntity createDepartment(CreateDepartmentRequest request) {
        DepartmentEntity department = new DepartmentEntity();
        department.setName(request.name());
        return departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public List<ContactMessageEntity> listMessages() {
        return contactMessageRepository.findAll();
    }

    @Transactional
    public ContactMessageEntity createMessage(CreateContactMessageRequest request) {
        DepartmentEntity department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new InvalidReferenceException("Department not found"));

        ContactMessageEntity message = new ContactMessageEntity();
        message.setDepartment(department);
        message.setName(request.name());
        message.setEmail(request.email());
        message.setMessage(request.message());

        return contactMessageRepository.save(message);
    }
}
