package org.aston.cours.service;

import lombok.RequiredArgsConstructor;
import org.aston.cours.dao.UserDao;
import org.aston.cours.model.User;

import java.util.List;


/**
 * Сервисный класс, отвечающий за логику работы с пользователями.
 * Делегирует операции объекту UserDao, который осуществляет доступ к данным.
 */
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    /**
     * Создает нового пользователя в системе.
     *
     * @param user объект, содержащий данные нового пользователя.
     */
    public void create(User user) {
        userDao.save(user);
    }

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param user объект с обновлёнными данными.
     */
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     */
    public void delete(int id) {
        User userForDelete = userDao.findById(id).orElseThrow();
        userDao.delete(userForDelete);
    }

    /**
     * Возвращает список всех пользователей, сохранённых в хранилище.
     *
     * @return список объектов.
     */
    public List<User> getAll() {
        return userDao.getAll();
    }

    /**
     * Ищет пользователя по идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     * @return объект.
     */
    public User findById(int id) {
        return userDao.findById(id).orElseThrow();
    }

    /**
     * Находит всех пользователей с указанным именем.
     *
     * @param name имя пользователя для поиска.
     * @return список пользователей с совпадающим именем.
     */
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    /**
     * Находит пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя
     * @return объект, если найден, иначе {@code null}
     */
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow();
    }

    /**
     * Находит всех пользователей указанного возраста.
     *
     * @param age возраст пользователя
     * @return список пользователей с данным возрастом
     */
    public List<User> findByAge(int age) {
        return userDao.findByAge(age);
    }
}
