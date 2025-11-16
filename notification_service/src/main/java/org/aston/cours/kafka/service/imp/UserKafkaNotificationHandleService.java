package org.aston.cours.kafka.service.imp;

import org.aston.cours.dto.UserDto;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.service.UserKafkaHandleService;
import org.aston.cours.mapper.UserMapper;
import org.aston.cours.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки сообщений о пользователях, получаемых из Kafka.
 * <p>
 * В зависимости от типа операции (создание или удаление пользователя)
 * вызывает соответствующие методы {@link NotificationService} для отправки уведомлений.
 * </p>
 */
@Service
public class UserKafkaNotificationHandleService implements UserKafkaHandleService {

    private final NotificationService notificationService;
    private final UserMapper userMapper;

    public UserKafkaNotificationHandleService(NotificationService notificationService, UserMapper userMapper) {
        this.notificationService = notificationService;
        this.userMapper = userMapper;
    }

    /**
     * Обрабатывает сообщение о пользователе, полученное из Kafka.
     * <p>
     * Сообщение преобразуется из {@link UserDtoKafka} в {@link UserDto} с помощью {@link UserMapper}.
     * </p>
     *
     * @param dto объект {@link UserDtoKafka}, содержащий данные о пользователе и тип операции
     */
    public void handleMessage(UserDtoKafka dto) {
        switch (dto.getOperation()) {
            case CREATE -> notificationService.sendCreateMessage(userMapper.convertToUserDto(dto));
            case DELETE -> notificationService.sendDeleteMessage(userMapper.convertToUserDto(dto));
        }
    }
}
