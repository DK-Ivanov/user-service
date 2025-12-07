package org.aston.cours.kafka.consumer;

import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.dto.UserOperation;
import org.aston.cours.kafka.producer.config.TestKafkaProducerConfig;
import org.aston.cours.kafka.service.UserKafkaHandleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@EnableKafka
@ActiveProfiles("test")
@SpringBootTest(
        properties = {
                "spring.cloud.config.enabled=false",
                "spring.cloud.discovery.enabled=false",
                "eureka.client.enabled=false",
        }, classes = {TestKafkaProducerConfig.class})
class UserKafkaConsumerIT {

    static KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("apache/kafka:latest")
    );

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeAll
    static void startKafka() {
        kafka.start();
    }

    @Autowired
    private KafkaTemplate<String, UserDtoKafka> kafkaTemplate;

    @MockBean
    private UserKafkaHandleService handleService;

    @Captor
    private ArgumentCaptor<UserDtoKafka> userCaptor;

    @Value("${spring.kafka.topic.user}")
    private String topicName;

    @Test
    @DisplayName("Consumer успешно принимает сообщение")
    void testConsumeUserMessageSucces() throws InterruptedException {
        Thread.sleep(2000); /// Костыль! Иначе Кафка не успевает подняться)
        UserDtoKafka user = new UserDtoKafka("Dima", "qwerty@ya.ru", UserOperation.CREATE);

        kafkaTemplate.send(topicName, user);
        kafkaTemplate.flush();

        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> Mockito.verify(handleService).handleMessage(userCaptor.capture()));
        assertEquals(user.getName(), userCaptor.getValue().getName());
        assertEquals(user.getEmail(), userCaptor.getValue().getEmail());
    }
}