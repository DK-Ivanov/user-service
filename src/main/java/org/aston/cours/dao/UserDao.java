package org.aston.cours.dao;

import org.aston.cours.model.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс доступа к данным (DAO) для работы с сущностью UserEntity.
 * <p>
 * Определяет базовые операции CRUD (создание, чтение, обновление, удаление)
 * и методы поиска пользователей по различным критериям.
 */
public interface UserDao {

    /**
     * Сохраняет нового пользователя в источнике данных.
     *
     * @param userEntity объект UserEntity, который нужно сохранить
     */
    void save(UserEntity userEntity);

    /**
     * Обновляет существующего пользователя в источнике данных.
     *
     * @param userEntity объект UserEntity с обновлёнными данными
     */
    void update(UserEntity userEntity);

    /**
     * Удаляет пользователя из источника данных.
     *
     * @param userEntity объект UserEntity, который необходимо удалить
     */
    void delete(UserEntity userEntity);

    /**
     * Находит пользователя по его уникальному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return Optional, содержащий найденного пользователя, или пустой, если пользователь не найден
     */
    Optional<UserEntity> findById(int id);

    /**
     * Находит всех пользователей с указанным именем.
     *
     * @param name имя пользователя
     * @return список пользователей с совпадающим именем; пустой список, если никого не найдено
     */
    List<UserEntity> findByName(String name);

    /**
     * Находит пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты
     * @return Optional, содержащий найденного пользователя, или пустой, если пользователь не найден
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Находит всех пользователей указанного возраста.
     *
     * @param age возраст пользователя
     * @return список пользователей данного возраста; пустой список, если никого не найдено
     */
    List<UserEntity> findByAge(int age);

    /**
     * Возвращает всех пользователей, хранящихся в источнике данных.
     *
     * @return список всех пользователей
     */
    List<UserEntity> getAll();
}
