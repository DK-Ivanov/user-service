package org.aston.cours.repository;

import org.aston.cours.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Testcontainers()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = org.aston.cours.Runner.class)
@Sql(statements = "CREATE SCHEMA IF NOT EXISTS user_data;\n" +
        "\n" +
        "CREATE TABLE IF NOT EXISTS user_data.users (\n" +
        "    id SERIAL PRIMARY KEY,\n" +
        "    name VARCHAR(255),\n" +
        "    email VARCHAR(255),\n" +
        "    age INT,\n" +
        "    created_at TIMESTAMP\n" +
        ");")
public class UserRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.4")
            .withDatabaseName("test_user_data")
            .withUsername("test_dev")
            .withPassword("test_123dev123");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("spring.jpa.properties.hibernate.default_schema", () -> "user_data");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.show-sql", () -> "true");
    }

    @Autowired
    private UserRepository userRepository;

    private UserEntity user = new UserEntity(
            "Dmitry",
            "qwerty@ya.ru",
            18,
            LocalDateTime.now())
            ;

    @BeforeEach
    public void resetData() {
        userRepository.deleteAll();
        userRepository.save(user);
    }

    @Test
    @DisplayName("Успешный поиск пользователя по имени в бд")
    void testFindByNameSucces() {
        List<UserEntity> result = userRepository.findByName(user.getName());

        assertEquals(List.of(user), result);
    }

    @Test
    @DisplayName("Успешный поиск пользователя по email в бд")
    void testFindByEmailSucces() {
        Optional<UserEntity> result = userRepository.findByEmail(user.getEmail());

        assertEquals(Optional.of(user), result);
    }

    @Test
    @DisplayName("Успешный поиск пользователя по возрасту в бд")
    void testFindByAgeSucces() {
        List<UserEntity> result = userRepository.findByAge(user.getAge());

        assertEquals(List.of(user), result);
    }

    @Test
    @DisplayName("Успешное удаление пользователя по email из бд")
    void testDeleteByEmailSucces() {
        userRepository.deleteByEmail(user.getEmail());
        Optional<UserEntity> result = userRepository.findById(user.getId());

        assertEquals(Optional.empty(), result);
    }
}

