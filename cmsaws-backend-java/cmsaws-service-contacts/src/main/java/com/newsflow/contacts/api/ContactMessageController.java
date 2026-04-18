package com.newsflow.contacts.api;

import com.newsflow.contacts.domain.ContactMessageEntity;
import com.newsflow.contacts.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts/messages")
public class ContactMessageController {

    private final ContactService contactService;

    public ContactMessageController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<ContactMessageResponse> list() {
        return contactService.listMessages().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public ContactMessageResponse create(@Valid @RequestBody CreateContactMessageRequest request) {
        return toResponse(contactService.createMessage(request));
    }

    private ContactMessageResponse toResponse(ContactMessageEntity message) {
        return new ContactMessageResponse(
                message.getId(),
                message.getDepartment().getId(),
                message.getDepartment().getName(),
                message.getName(),
                message.getEmail(),
                message.getMessage()
        );
    }
}