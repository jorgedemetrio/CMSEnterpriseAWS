package com.newsflow.users.api;

import com.newsflow.users.integration.CoreHealthClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users/integration")
public class IntegrationController {

    private final CoreHealthClient coreHealthClient;

    public IntegrationController(CoreHealthClient coreHealthClient) {
        this.coreHealthClient = coreHealthClient;
    }

    @GetMapping("/core-health")
    public Map<String, String> coreHealth() {
        return Map.of("coreHealth", coreHealthClient.health());
    }
}
