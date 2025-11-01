package org.aston.course.dao.imp;

import org.aston.cours.dao.imp.UserDaoImpl;
import org.aston.cours.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserEntityDaoImplIT {

    @Container
    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.4")
            .withDatabaseName("user_management_test")
            .withUsername("dev_test")
            .withPassword("123dev123_test");

    private SessionFactory sessionFactory;
    private UserDaoImpl userDao;
    private UserEntity userEntity;

    @BeforeAll
    public void configure() {
        postgres.start();

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.default_schema", "user_data");
        configuration.addAnnotatedClass(UserEntity.class);

        sessionFactory = configuration.buildSessionFactory();
        userDao = new UserDaoImpl(sessionFactory);

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(
                    "CREATE SCHEMA IF NOT EXISTS user_data; " +
                            "CREATE TABLE IF NOT EXISTS user_data.users (" +
                            "id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(255), " +
                            "email VARCHAR(255), " +
                            "age INT, " +
                            "created_at TIMESTAMP" +
                            ");"
            ).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @BeforeEach
    public void resetTableAndUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createMutationQuery("DELETE FROM UserEntity").executeUpdate();
            session.getTransaction().commit();
        }
        userEntity = new UserEntity(
                "Dima",
                "qwerty123@ya.ru",
                12,
                LocalDateTime.now());
    }

    @AfterAll
    public void close() {
        if (sessionFactory != null) sessionFactory.close();
    }

    @Test
    @DisplayName("Успешное сохранение пользователя в бд")
    public void testSaveUserSucces() {
        userDao.save(userEntity);

        UserEntity result = findUserById(userEntity.getId());
        assertEquals(userEntity, result);
    }

    @Test
    @DisplayName("Успешное изменение пользователя в бд")
    public void testUpdateUserSucces() {
        addUserToDB(userEntity);
        UserEntity updatedUserEntity = new UserEntity(
                userEntity.getId(),
                "Amid",
                userEntity.getEmail(),
                userEntity.getAge(),
                userEntity.getCreatedAt()
        );

        userDao.update(updatedUserEntity);

        UserEntity result = findUserById(userEntity.getId());
        assertEquals(updatedUserEntity.getName(), result.getName());
    }

    @Test
    @DisplayName("Успешное удаление пользователя из бд")
    public void testDeleteUserSucces() {
        addUserToDB(userEntity);

        userDao.delete(userEntity);

        UserEntity result = findUserById(userEntity.getId());
        assertNull(result);
    }

    private UserEntity findUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(UserEntity.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Успешный поиск пользователя по id в бд")
    public void testFindByIdSucces() {
        addUserToDB(userEntity);

        Optional<UserEntity> result = userDao.findById(userEntity.getId());

        assertEquals(Optional.of(userEntity), result);
    }

    @Test
    @DisplayName("Успешный поиск пользователя по имени в бд")
    public void testFindByNameSucces() {
        addUserToDB(userEntity);

        List<UserEntity> result = userDao.findByName(userEntity.getName());

        assertEquals(List.of(userEntity), result);
    }

    @Test
    @DisplayName("Успешный поиск пользователя по email в бд")
    public void testFindByEmailSucces() {
        addUserToDB(userEntity);

        Optional<UserEntity> result = userDao.findByEmail(userEntity.getEmail());

        assertEquals(Optional.of(userEntity), result);
    }

    @Test
    @DisplayName("Успешный поиск пользователя по возрвсту в бд")
    public void testFindByAgeSucces() {
        addUserToDB(userEntity);

        List<UserEntity> result = userDao.findByAge(userEntity.getAge());

        assertEquals(List.of(userEntity), result);
    }

    @Test
    @DisplayName("Успешное получение всех пользователей из бд")
    public void testGetAllSucces() {
        UserEntity userEntity2 = new UserEntity("Kolya", "qw1@ya.ru", 13, LocalDateTime.now());
        addUserToDB(userEntity);
        addUserToDB(userEntity2);

        List<UserEntity> result = userDao.getAll();

        assertEquals(List.of(userEntity, userEntity2), result);
    }

    private void addUserToDB(UserEntity userEntity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(userEntity);
            session.getTransaction().commit();
        }
    }


}
