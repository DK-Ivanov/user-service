package org.aston.cours.hateoas.model;

import org.aston.cours.dto.UserDto;
import org.springframework.hateoas.RepresentationModel;

/**
 * HATEOAS-модель, представляющая данные пользователя вместе с набором
 * гипермедиа-ссылок.
 */
public class UserModel extends RepresentationModel<UserModel> {
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

    public UserModel(UserDto user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.age = user.getAge();
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
