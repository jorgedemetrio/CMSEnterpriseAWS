package com.newsflow.contacts.api;

import com.newsflow.contacts.api.mapper.ContactMessageMapper;
import com.newsflow.contacts.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts/messages")
public class ContactMessageController {

    private final ContactService contactService;
    private final ContactMessageMapper contactMessageMapper;

    public ContactMessageController(ContactService contactService, ContactMessageMapper contactMessageMapper) {
        this.contactService = contactService;
        this.contactMessageMapper = contactMessageMapper;
    }

    @GetMapping
    public List<ContactMessageResponse> list() {
        return contactService.listMessages().stream()
                .map(contactMessageMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ContactMessageResponse create(@Valid @RequestBody CreateContactMessageRequest request) {
        return contactMessageMapper.toResponse(contactService.createMessage(request));
    }
}