package org.aston.cours.kafka.producer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.aston.cours.kafka.consumer.config.TestKafkaConsumerConfig;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.dto.UserOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(properties = "spring.profiles.active=test", classes = {TestKafkaConsumerConfig.class})
class UserKafkaProducerIT {

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
    private UserKafkaProducer userKafkaProducer;
    @Autowired
    private ConsumerFactory<String, UserDtoKafka> testConsumerFactory;

    private Consumer<String, UserDtoKafka> consumer;

    @Value("${spring.kafka.topic.user}")
    private String topicName;


    @BeforeEach
    void setUp() {
        consumer = testConsumerFactory.createConsumer();
        consumer.subscribe(List.of(topicName));
    }

    @Test
    @DisplayName("Успешное отправление пользователя в кафку")
    void testSendUserToKafkaSucces() {
        UserDtoKafka user = new UserDtoKafka("Dima", "qwerty@ya.ru", UserOperation.CREATE);

        userKafkaProducer.sendUserToKafka(user);
        ConsumerRecords<String, UserDtoKafka> dataFromKafka = consumer.poll(Duration.ofSeconds(4));

        UserDtoKafka result = dataFromKafka.iterator().next().value();
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
    }
}