package org.aston.cours.kafka.service.imp;

import org.aston.cours.entity.UserEntity;
import org.aston.cours.kafka.dto.UserOperation;
import org.aston.cours.kafka.producer.UserKafkaProducer;
import org.aston.cours.kafka.service.UserKafkaService;
import org.aston.cours.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserKafkaService}, отвечающая за отправку сообщений в Kafka
 * при создании и удалении пользователей.
 * <p>
 * Данный сервис преобразует сущность {@link UserEntity} в DTO, пригодное для передачи через Kafka,
 * с помощью {@link UserMapper}, и делегирует публикацию сообщений в {@link UserKafkaProducer}.
 */
@Service
public class UserKafkaServiceImpl implements UserKafkaService {

    private final UserKafkaProducer userKafkaProducer;
    private final UserMapper userMapper;

    public UserKafkaServiceImpl(UserKafkaProducer userKafkaProducer, UserMapper userMapper) {
        this.userKafkaProducer = userKafkaProducer;
        this.userMapper = userMapper;
    }

    /**
     * Отправляет в Kafka сообщение о создании пользователя.
     *
     * @param user сущность {@link UserEntity}, информация о которой будет отправлена
     */
    @Override
    public void create(UserEntity user) {
        userKafkaProducer.sendUserToKafka(userMapper.dtoKafkaOfEntity(user, UserOperation.CREATE));
    }

    /**
     * Отправляет в Kafka сообщение об удалении пользователя.
     *
     * @param user сущность {@link UserEntity}, информация о которой будет отправлена
     */
    @Override
    public void delete(UserEntity user) {
        userKafkaProducer.sendUserToKafka(userMapper.dtoKafkaOfEntity(user, UserOperation.DELETE));
    }
}
