package com.newsflow.contacts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "cmsaws-service-contacts up";
    }
}