package org.aston.cours.kafka.service;

import org.aston.cours.entity.UserEntity;

/**
 * Сервис для отправки сообщений о событиях, связанных с пользователями, в Kafka.
 * <p>
 * Реализация данного интерфейса отвечает за публикацию сообщений
 * при создании и удалении сущностей UserEntity.
 */
public interface UserKafkaService {

    /**
     * Отправляет сообщение в Kafka о создании пользователя.
     *
     * @param user сущность UserEntity, информация о которой будет отправлена
     */
    void create(UserEntity user);

    /**
     * Отправляет сообщение в Kafka об удалении пользователя.
     *
     * @param user сущность UserEntity, информация о которой будет отправлена
     */
    void delete(UserEntity user);
}
