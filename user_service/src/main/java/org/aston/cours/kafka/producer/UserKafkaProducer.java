package org.aston.cours.kafka.producer;

import org.aston.cours.kafka.dto.UserDtoKafka;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaProducer {

    private final KafkaTemplate<String, UserDtoKafka> kafkaTemplate;

    @Value("${spring.kafka.topic.user}")
    private String topicName;

    public UserKafkaProducer(KafkaTemplate<String, UserDtoKafka> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserToKafka(UserDtoKafka user) {
        kafkaTemplate.send(topicName, user);
    }
}
