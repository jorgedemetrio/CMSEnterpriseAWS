package com.newsflow.contacts.service;

import com.newsflow.contacts.api.CreateContactMessageRequest;
import com.newsflow.contacts.api.CreateDepartmentRequest;
import com.newsflow.contacts.api.InvalidReferenceException;
import com.newsflow.contacts.domain.ContactMessageEntity;
import com.newsflow.contacts.domain.DepartmentEntity;
import com.newsflow.contacts.repository.ContactMessageRepository;
import com.newsflow.contacts.repository.DepartmentRepository;
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
class ContactServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ContactMessageRepository contactMessageRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    void shouldListDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(new DepartmentEntity()));

        List<DepartmentEntity> result = contactService.listDepartments();

        assertTrue(result.size() == 1);
    }

    @Test
    void shouldCreateDepartment() {
        CreateDepartmentRequest request = new CreateDepartmentRequest("Comercial");
        when(departmentRepository.save(any(DepartmentEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DepartmentEntity created = contactService.createDepartment(request);

        assertTrue("Comercial".equals(created.getName()));
    }

    @Test
    void shouldListMessages() {
        when(contactMessageRepository.findAll()).thenReturn(List.of(new ContactMessageEntity()));

        List<ContactMessageEntity> result = contactService.listMessages();

        assertTrue(result.size() == 1);
    }

    @Test
    void shouldCreateMessage() {
        UUID departmentId = UUID.randomUUID();
        DepartmentEntity department = new DepartmentEntity();
        CreateContactMessageRequest request = new CreateContactMessageRequest(
                departmentId,
                "Maria",
                "maria@example.com",
                "Mensagem"
        );

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(contactMessageRepository.save(any(ContactMessageEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ContactMessageEntity created = contactService.createMessage(request);

        assertTrue("Maria".equals(created.getName()));
        assertTrue("maria@example.com".equals(created.getEmail()));
        assertTrue("Mensagem".equals(created.getMessage()));
        assertTrue(created.getDepartment() == department);
    }

    @Test
    void shouldFailCreateMessageForMissingDepartment() {
        UUID departmentId = UUID.randomUUID();
        CreateContactMessageRequest request = new CreateContactMessageRequest(
                departmentId,
                "Maria",
                "maria@example.com",
                "Mensagem"
        );
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(InvalidReferenceException.class, () -> contactService.createMessage(request));
    }
}
