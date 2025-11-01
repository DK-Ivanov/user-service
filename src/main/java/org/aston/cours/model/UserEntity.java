package org.aston.cours.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import lombok.*;

/**
 * Сущность пользователя для хранения в базе данных.
 */
@Entity
@Table(name = "users", schema = "user_data")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {

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
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;

    /**
     * Конструктор для создания нового пользователя без указания id.
     *
     * @param name      имя пользователя
     * @param email     email пользователя
     * @param age       возраст пользователя
     * @param createdAt дата и время создания пользователя
     */
    public UserEntity(String name, String email, Integer age, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }
}

