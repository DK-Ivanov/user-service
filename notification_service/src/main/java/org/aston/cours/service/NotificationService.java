package org.aston.cours.service;

import org.aston.cours.dto.UserDto;
import org.aston.cours.dto.UserMessageDto;

/**
 * Интерфейс {@code NotificationService} определяет операции для отправки уведомлений пользователям.
 * <p>
 * Реализации этого интерфейса могут отправлять уведомления через различные каналы.
 * </p>
 */
public interface NotificationService {

    /**
     * Отправляет уведомление пользователю о создании аккаунта.
     *
     * @param dto объект {@link UserDto}, содержащий информацию о пользователе.
     * */
    void sendCreateMessage(UserDto dto);

    /**
     * Отправляет уведомление пользователю об удалении аккаунта.
     *
     * @param dto объект {@link UserDto}, содержащий информацию о пользователе.
     */
    void sendDeleteMessage(UserDto dto);

    /**
     * Отправляет произвольное уведомление пользователю.
     *
     * @param message объект {@link UserMessageDto}, содержащий email получателя,
     *                тему письма и текст сообщения.
     */
    void sendCustomMessage(UserMessageDto message);
}
