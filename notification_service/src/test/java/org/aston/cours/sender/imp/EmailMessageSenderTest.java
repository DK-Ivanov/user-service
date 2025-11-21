package org.aston.cours.sender.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailMessageSenderTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailMessageSender emailMessageSender;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    private SimpleMailMessage message;

    @BeforeEach
    void initMessage() {
        message = new SimpleMailMessage();
        message.setTo("test@ya.ru");
        message.setSubject("test_subject");
        message.setText("test_text");
    }

    @Test
    @DisplayName("Успешная отправка сообщения")
    void testSendMessageSucces() {
        emailMessageSender.sendMessage(message.getTo()[0], message.getSubject(), message.getText());
        verify(javaMailSender).send(messageCaptor.capture());
        assertEquals(message, messageCaptor.getValue());
    }
}