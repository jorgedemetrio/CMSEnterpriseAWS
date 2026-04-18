package com.newsflow.contacts.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsflow.contacts.api.mapper.ContactMessageMapper;
import com.newsflow.contacts.api.mapper.DepartmentMapper;
import com.newsflow.contacts.domain.ContactMessageEntity;
import com.newsflow.contacts.domain.DepartmentEntity;
import com.newsflow.contacts.service.ContactService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({DepartmentController.class, ContactMessageController.class})
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ContactsControllersWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    @MockBean
    private DepartmentMapper departmentMapper;

    @MockBean
    private ContactMessageMapper contactMessageMapper;

    @Test
    void shouldListDepartments() throws Exception {
        DepartmentEntity department = new DepartmentEntity();
        department.setName("Suporte");

        when(contactService.listDepartments()).thenReturn(List.of(department));
        when(departmentMapper.toResponse(any(DepartmentEntity.class))).thenAnswer(invocation -> {
            DepartmentEntity mapped = invocation.getArgument(0);
            return new DepartmentResponse(mapped.getId(), mapped.getName());
        });

        mockMvc.perform(get("/api/contacts/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Suporte"));
    }

    @Test
    void shouldCreateDepartment() throws Exception {
        DepartmentEntity department = new DepartmentEntity();
        department.setName("Comercial");

        when(contactService.createDepartment(any(CreateDepartmentRequest.class))).thenReturn(department);
        when(departmentMapper.toResponse(any(DepartmentEntity.class))).thenAnswer(invocation -> {
            DepartmentEntity mapped = invocation.getArgument(0);
            return new DepartmentResponse(mapped.getId(), mapped.getName());
        });

        CreateDepartmentRequest request = new CreateDepartmentRequest("Comercial");

        mockMvc.perform(post("/api/contacts/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Comercial"));
    }

    @Test
    void shouldListMessages() throws Exception {
        DepartmentEntity department = new DepartmentEntity();
        department.setName("RH");

        ContactMessageEntity message = new ContactMessageEntity();
        message.setDepartment(department);
        message.setName("Ana");
        message.setEmail("ana@example.com");
        message.setMessage("Mensagem");

        when(contactService.listMessages()).thenReturn(List.of(message));
        when(contactMessageMapper.toResponse(any(ContactMessageEntity.class))).thenAnswer(invocation -> {
            ContactMessageEntity mapped = invocation.getArgument(0);
            return new ContactMessageResponse(
                mapped.getId(),
                mapped.getDepartment() != null ? mapped.getDepartment().getId() : null,
                mapped.getDepartment() != null ? mapped.getDepartment().getName() : null,
                mapped.getName(),
                mapped.getEmail(),
                mapped.getMessage()
            );
        });

        mockMvc.perform(get("/api/contacts/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ana"))
                .andExpect(jsonPath("$[0].departmentName").value("RH"));
    }

    @Test
    void shouldCreateMessage() throws Exception {
        DepartmentEntity department = new DepartmentEntity();
        department.setName("Financeiro");

        ContactMessageEntity message = new ContactMessageEntity();
        message.setDepartment(department);
        message.setName("Carlos");
        message.setEmail("carlos@example.com");
        message.setMessage("Preciso de ajuda");

        when(contactService.createMessage(any(CreateContactMessageRequest.class))).thenReturn(message);
        when(contactMessageMapper.toResponse(any(ContactMessageEntity.class))).thenAnswer(invocation -> {
            ContactMessageEntity mapped = invocation.getArgument(0);
            return new ContactMessageResponse(
                mapped.getId(),
                mapped.getDepartment() != null ? mapped.getDepartment().getId() : null,
                mapped.getDepartment() != null ? mapped.getDepartment().getName() : null,
                mapped.getName(),
                mapped.getEmail(),
                mapped.getMessage()
            );
        });

        CreateContactMessageRequest request = new CreateContactMessageRequest(
                UUID.randomUUID(),
                "Carlos",
                "carlos@example.com",
                "Preciso de ajuda"
        );

        mockMvc.perform(post("/api/contacts/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carlos"))
                .andExpect(jsonPath("$.departmentName").value("Financeiro"));
    }

    @Test
    void shouldReturnValidationDetailsForInvalidMessagePayload() throws Exception {
        String invalidPayload = "{\"departmentId\":null,\"name\":\"\",\"email\":\"invalido\",\"message\":\"\"}";

        mockMvc.perform(post("/api/contacts/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details.length()").value(4));
    }
}
