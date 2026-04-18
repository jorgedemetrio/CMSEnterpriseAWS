package com.newsflow.worker.forum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ForumWorkerApplication.class)
class ForumWorkerApplicationTest {

    @Test
    void contextLoads() {
        // Smoke test: garante bootstrap do worker no ciclo de teste.
    }
}
