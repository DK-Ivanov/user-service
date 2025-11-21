package org.aston.cours.dto;

/**
 * DTO для передачи информации о пользователе через HTTP-запросы.
 * <p>
 * Содержит базовые данные пользователя: имя и email.
 * </p>
 */
public class UserDto {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Email пользователя.
     */
    private String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

