package com.newsflow.worker.forum;

import com.newsflow.worker.forum.repository.ForumEventConsumptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(
        classes = ForumWorkerApplication.class,
        properties = {
                "spring.flyway.enabled=false",
                "spring.kafka.listener.auto-startup=false",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
        }
)
class ForumWorkerApplicationTest {

    @MockBean
    private ForumEventConsumptionRepository consumptionRepository;

    @Test
    void contextLoads() {
        // Smoke test: garante bootstrap do worker no ciclo de teste.
    }
}
