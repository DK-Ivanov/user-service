package org.aston.cours.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность пользователя для хранения в базе данных.
 */
@Entity
@Table(name = "users", schema = "user_data")
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

    public UserEntity(int id, String name, String email, Integer age, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

    public UserEntity() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age);
    }
}

