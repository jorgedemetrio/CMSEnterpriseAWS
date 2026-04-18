package com.newsflow.forum;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forum")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "cmsaws-service-forum up";
    }
}