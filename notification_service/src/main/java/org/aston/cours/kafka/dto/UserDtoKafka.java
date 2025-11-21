package org.aston.cours.kafka.dto;

/**
 * DTO для передачи информации о пользователе из Kafka.
 * <p>
 * Содержит имя пользователя, email и тип операции (CREATE или DELETE).
 * Используется для десериализации сообщений из Kafka.
 * </p>
 */
public class UserDtoKafka {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Email пользователя.
     */
    private String email;

    /**
     * Тип операции с пользователем.
     */
    private UserOperation operation;

    public UserDtoKafka() {
    }

    public UserDtoKafka(String name, String email, UserOperation operation) {
        this.name = name;
        this.email = email;
        this.operation = operation;
    }

    public void setOperation(UserOperation operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserOperation getOperation() {
        return operation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
