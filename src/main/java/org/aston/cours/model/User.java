package org.aston.cours.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность пользователя для хранения в базе данных.
 */
@Entity
@Table(name = "user_data.users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя пользователя.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Электронная почта пользователя.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Возраст пользователя.
     */
    @Column(nullable = false)
    private Integer age;

    /**
     * Дата и время создания записи пользователя.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Конструктор для создания нового пользователя без указания id.
     *
     * @param name      имя пользователя
     * @param email     email пользователя
     * @param age       возраст пользователя
     * @param createdAt дата и время создания пользователя
     */
    public User(String name, String email, Integer age, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }
}

