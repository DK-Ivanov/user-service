package org.aston.cours.service.imp;

import org.aston.cours.entity.UserEntity;
import org.aston.cours.exception.UserNotFoundException;
import org.aston.cours.repository.UserRepository;
import org.aston.cours.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Сервисный класс, отвечающий за логику работы с пользователями.
 * Делегирует операции объекту UserDao, который осуществляет доступ к данным.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создает нового пользователя в системе.
     *
     * @param user объект, содержащий данные нового пользователя.
     */
    @Transactional
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param user объект с обновлёнными данными.
     */
    @Transactional
    public void update(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     */
    @Transactional
    public void delete(int id) {
        UserEntity userEntityForDelete = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userEntityForDelete);
    }

    /**
     * Удаляет пользователя по его email.
     *
     * @param email адрес электронной почты пользователя.
     */
    @Transactional
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    /**
     * Возвращает список всех пользователей, сохранённых в хранилище.
     *
     * @return список объектов.
     */
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * Ищет пользователя по идентификатору.
     *
     * @param id уникальный идентификатор пользователя.
     * @return объект.
     */
    public UserEntity findById(int id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Находит всех пользователей с указанным именем.
     *
     * @param name имя пользователя для поиска.
     * @return список пользователей с совпадающим именем.
     */
    public List<UserEntity> findByName(String name) {
        return userRepository.findByName(name);
    }

    /**
     * Находит пользователя по адресу электронной почты.
     *
     * @param email адрес электронной почты пользователя
     * @return объект, если найден, иначе {@code null}
     */
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Находит всех пользователей указанного возраста.
     *
     * @param age возраст пользователя
     * @return список пользователей с данным возрастом
     */
    public List<UserEntity> findByAge(int age) {
        return userRepository.findByAge(age);
    }
}
