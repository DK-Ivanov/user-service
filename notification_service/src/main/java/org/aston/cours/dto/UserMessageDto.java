package org.aston.cours.dto;

/**
 * DTO для передачи произвольного сообщения пользователю через HTTP-запрос.
 * <p>
 * Содержит email получателя, тему письма и текст сообщения.
 * </p>
 */
public class UserMessageDto {

    /**
     * Email получателя сообщения.
     */
    private String email;

    /**
     * Тема сообщения.
     */
    private String subject;

    /**
     * Текст сообщения.
     */
    private String message;

    public UserMessageDto(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
