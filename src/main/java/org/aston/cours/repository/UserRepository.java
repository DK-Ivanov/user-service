package org.aston.cours.repository;

import org.aston.cours.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

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
     * Удаляет пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты
     */
    void deleteByEmail(String email);
}
