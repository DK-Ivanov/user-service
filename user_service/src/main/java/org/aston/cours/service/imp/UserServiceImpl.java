package org.aston.cours.service.imp;

import org.aston.cours.entity.UserEntity;
import org.aston.cours.exception.UserNotFoundException;
import org.aston.cours.kafka.service.UserKafkaService;
import org.aston.cours.repository.UserRepository;
import org.aston.cours.service.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Сервисный класс, отвечающий за логику работы с пользователями.
 * Делегирует операции объекту UserDao, который осуществляет доступ к данным.
 */
@Service
public class UserServiceImpl implements EntityService<UserEntity> {

    private final UserRepository userRepository;
    private final UserKafkaService userKafkaService;

    public UserServiceImpl(UserRepository userRepository, UserKafkaService userKafkaService) {
        this.userRepository = userRepository;
        this.userKafkaService = userKafkaService;
    }

    /**
     * Создает нового пользователя в системе и
     * делегирует {@link UserKafkaService} отправку сообщения в kafka о добавлении пользователя.
     *
     * @param user объект, содержащий данные нового пользователя.
     */
    @Transactional
    public void save(UserEntity user) {
        userRepository.save(user);
        userKafkaService.create(user);
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
     * Удаляет пользователя по его идентификатору и
     * делегирует {@link UserKafkaService} отправку сообщения в kafka об удалении пользователя.
     *
     * @param id уникальный идентификатор пользователя.
     */
    @Transactional
    public void delete(int id) {
        UserEntity userEntityForDelete = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userEntityForDelete);
        userKafkaService.delete(userEntityForDelete);
    }

    /**
     * Удаляет пользователя по его email и
     * делегирует {@link UserKafkaService} отправку сообщения в kafka об удалении пользователя.
     *
     * @param email адрес электронной почты пользователя.
     */
    @Transactional
    public void delete(String email) {
        UserEntity userEntityForDelete = findByEmail(email);
        userRepository.delete(userEntityForDelete);
        userKafkaService.delete(userEntityForDelete);
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
