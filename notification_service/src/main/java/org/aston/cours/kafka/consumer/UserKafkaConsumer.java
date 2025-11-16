package org.aston.cours.kafka.consumer;

import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.service.UserKafkaHandleService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaConsumer {

    private final UserKafkaHandleService userKafkaHandleService;

    public UserKafkaConsumer(UserKafkaHandleService userKafkaHandleService) {
        this.userKafkaHandleService = userKafkaHandleService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.user}",
            groupId = "notification_service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeUser(UserDtoKafka dto) {
        userKafkaHandleService.handleMessage(dto);
    }
}
