package com.newsflow.users.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "coreHealthClient", url = "${clients.core.url:http://localhost:8080}")
public interface CoreHealthClient {

    @GetMapping("/api/core/health")
    String health();
}
