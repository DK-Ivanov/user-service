package org.aston.cours.kafka.service;

import org.aston.cours.kafka.dto.UserDtoKafka;

/**
 * Интерфейс {@code UserKafkaHandleService} определяет контракт для обработки сообщений
 * о пользователях, получаемых из Kafka.
 * <p>
 * Реализации этого интерфейса должны обрабатывать входящие сообщения.
 * </p>
 */
public interface UserKafkaHandleService {

    /**
     * Обрабатывает сообщение о пользователе, полученное из Kafka.
     *
     * @param dto объект {@link UserDtoKafka}, содержащий данные о пользователе.
     */
    void handleMessage(UserDtoKafka dto);
}
