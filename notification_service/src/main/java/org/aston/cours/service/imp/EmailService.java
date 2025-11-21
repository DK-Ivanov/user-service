package org.aston.cours.service.imp;

import org.aston.cours.dto.UserDto;
import org.aston.cours.dto.UserMessageDto;
import org.aston.cours.sender.MessageSender;
import org.aston.cours.service.NotificationService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * Реализация интерфейса {@link NotificationService} для отправки электронных писем
 * с использованием {@link JavaMailSender}.
 * <p>
 * Поддерживает отправку уведомлений о создании и удалении аккаунтов,
 * а также отправку произвольных сообщений пользователям.
 * </p>
 */
@Service
public class EmailService implements NotificationService {

    private final MessageSender messageSender;

    public EmailService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Отправляет электронное письмо пользователю с уведомлением о создании аккаунта.
     *
     * @param dto объект {@link UserDto}, содержащий данные пользователя.
     */
    @Override
    public void sendCreateMessage(UserDto dto) {
        String text = "Здравствуйте %s! Ваш аккаунт на сайте ваш сайт был успешно создан."
                .formatted(dto.getName());
        messageSender.sendMessage(dto.getEmail(), "Создание пользователя", text);
    }

    /**
     * Отправляет электронное письмо пользователю с уведомлением об удалении аккаунта.
     *
     * @param dto объект {@link UserDto}, содержащий данные пользователя
     */
    @Override
    public void sendDeleteMessage(UserDto dto) {
        String text = "Здравствуйте %s! Ваш аккаунт был удалён.".formatted(dto.getName());
        messageSender.sendMessage(dto.getEmail(), "Удаление пользователя", text);
    }

    /**
     * Отправляет произвольное электронное письмо пользователю.
     *
     * @param message объект {@link UserMessageDto}, содержащий email получателя,
     *                тему письма и текст сообщения
     */
    @Override
    public void sendCustomMessage(UserMessageDto message) {
        messageSender.sendMessage(message.getEmail(), message.getSubject(), message.getMessage());
    }
}
