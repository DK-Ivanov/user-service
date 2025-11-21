package org.aston.cours.dto;

/**
 * Объект передачи данных (DTO), представляющий пользователя системы.
 * Используется для передачи данных между слоями приложения
 * без раскрытия внутренней структуры сущности.
 */
public class UserDto {
    /**
     * Полное имя пользователя.
     * */
    protected String name;
    /**
     * Адрес электронной почты пользователя.
     * */
    protected String email;
    /**
     * Возраст пользователя.
     * */
    protected int age;

    public UserDto() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}

