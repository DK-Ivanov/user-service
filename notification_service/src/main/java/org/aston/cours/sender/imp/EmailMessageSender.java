package org.aston.cours.sender.imp;

import org.aston.cours.sender.MessageSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Реализация интерфейса {@link MessageSender}, использующая {@link JavaMailSender}
 * для отправки электронных писем.
 * <p>
 * Этот класс формирует текстовое сообщение и отправляет его через SMTP.
 * </p>
 */
@Service
public class EmailMessageSender implements MessageSender {

    private final JavaMailSender mailSender;

    public EmailMessageSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Отправляет электронное письмо указанному получателю.
     *
     * @param to      email получателя
     * @param subject тема письма
     * @param text    текст письма
     */
    @Override
    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
