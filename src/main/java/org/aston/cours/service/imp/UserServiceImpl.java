package org.aston.cours.service.imp;

import lombok.RequiredArgsConstructor;
import org.aston.cours.dao.UserDao;
import org.aston.cours.model.UserEntity;
import org.aston.cours.service.UserService;

import java.util.List;


/**
 * Сервисный класс, отвечающий за логику работы с пользователями.
 * Делегирует операции объекту UserDao, который осуществляет доступ к данным.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    /**
     * Создает нового пользователя в системе.
     *
     * @param userEntity объект, содержащий данные нового пользователя.
     */
    public void create(UserEntity userEntity) {
        userDao.save(userEntity);
    }

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param userEntity объект с обновлёнными данными.
     */
    public void update(UserEntity userEntity) {
        userDao.update(userEntity);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     */
    public void delete(int id) {
        UserEntity userEntityForDelete = userDao.findById(id).orElseThrow();
        userDao.delete(userEntityForDelete);
    }

    /**
     * Возвращает список всех пользователей, сохранённых в хранилище.
     *
     * @return список объектов.
     */
    public List<UserEntity> getAll() {
        return userDao.getAll();
    }

    /**
     * Ищет пользователя по идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     * @return объект.
     */
    public UserEntity findById(int id) {
        return userDao.findById(id).orElseThrow();
    }

    /**
     * Находит всех пользователей с указанным именем.
     *
     * @param name имя пользователя для поиска.
     * @return список пользователей с совпадающим именем.
     */
    public List<UserEntity> findByName(String name) {
        return userDao.findByName(name);
    }

    /**
     * Находит пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя
     * @return объект, если найден, иначе {@code null}
     */
    public UserEntity findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow();
    }

    /**
     * Находит всех пользователей указанного возраста.
     *
     * @param age возраст пользователя
     * @return список пользователей с данным возрастом
     */
    public List<UserEntity> findByAge(int age) {
        return userDao.findByAge(age);
    }
}
