package org.aston.cours.service.imp;

import org.aston.cours.dto.UserDto;
import org.aston.cours.dto.UserMessageDto;
import org.aston.cours.sender.MessageSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<String> emailCapture;
    @Captor
    private ArgumentCaptor<String> subjectCapture;
    @Captor
    private ArgumentCaptor<String> messageCapture;

    private UserDto user = new UserDto("Dima", "qwerty123@ya.ru");

    @Test
    @DisplayName("Успешная отправка уведомления о создании пользователя")
    void sendCreateMessage() {
        emailService.sendCreateMessage(user);

        verify(messageSender).sendMessage(
                emailCapture.capture(),
                subjectCapture.capture(),
                messageCapture.capture()
        );
        assertEquals(user.getEmail(), emailCapture.getValue());
        assertEquals("Создание пользователя", subjectCapture.getValue());
        assertEquals(
                "Здравствуйте %s! Ваш аккаунт на сайте ваш сайт был успешно создан."
                        .formatted(user.getName()),
                messageCapture.getValue()
        );
    }

    @Test
    @DisplayName("Успешная отправка уведомления об удалении пользователя")
    void sendDeleteMessage() {
        emailService.sendDeleteMessage(user);

        verify(messageSender).sendMessage(
                emailCapture.capture(),
                subjectCapture.capture(),
                messageCapture.capture()
        );
        assertEquals(user.getEmail(), emailCapture.getValue());
        assertEquals("Удаление пользователя", subjectCapture.getValue());
        assertEquals(
                "Здравствуйте %s! Ваш аккаунт был удалён.".formatted(user.getName()),
                messageCapture.getValue()
        );
    }

    @Test
    @DisplayName("Успешная отправка кастомного уведомления пользователю")
    void sendCustomMessage() {
        String subject = "subject";
        String message = "message";
        UserMessageDto messageDto = new UserMessageDto(
                user.getEmail(),
                subject,
                message
        );

        emailService.sendCustomMessage(messageDto);

        verify(messageSender).sendMessage(
                emailCapture.capture(),
                subjectCapture.capture(),
                messageCapture.capture()
        );
        assertEquals(user.getEmail(), emailCapture.getValue());
        assertEquals(subject, subjectCapture.getValue());
        assertEquals(message, messageCapture.getValue());

    }
}