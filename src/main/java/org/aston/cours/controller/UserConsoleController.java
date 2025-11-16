package org.aston.cours.controller;

import org.aston.cours.entity.UserEntity;
import org.aston.cours.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Контроллер консольного интерфейса приложения для управления пользователями.
 * Отвечает за взаимодействие с пользователем через консоль:
 * вывод меню, получение ввода, валидацию и вызовы соответствующих методов UserServiceImpl.
 */
@Component
public class UserConsoleController {

    /**
     * Сканер для чтения пользовательского ввода из консоли.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Сервис для выполнения бизнес-операций над пользователями.
     */
    private final UserService userServiceImpl;

    public UserConsoleController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Точка входа в консольное приложение.
     * Выводит главное меню и обрабатывает выбор пользователя.
     */
    public void start() {
        System.out.println("\n".repeat(15));
        System.out.println(
                """
                        Выберите команду:
                        1 - создать пользователя.
                        2 - изменить существующего пользователя.
                        3 - удалить пользователя.
                        4 - посмотреть всех пользователей.
                        5 - найти пользователя по id.
                        6 - найти пользователя по имени.
                        7 - найти пользователя по email.
                        8 - найти пользователя по возрасту.
                        9 - выйти.
                        """
        );
        int commandNumber = cleanNextInt();
        choseCommand(commandNumber);
    }

    /**
     * Обрабатывает выбранную пользователем команду и вызывает соответствующий метод.
     *
     * @param commandNumber номер команды из меню
     */
    private void choseCommand(int commandNumber) {
        switch (commandNumber) {
            case 1 -> create();
            case 2 -> update();
            case 3 -> delete();
            case 4 -> showAll(userServiceImpl.getAll());
            case 5 -> findById();
            case 6 -> findByName();
            case 7 -> findByEmail();
            case 8 -> findByAge();
            case 9 -> System.exit(0);
        }
        System.out.println("Введите любой символ, чтобы продолжить.");
        scanner.nextLine();
        start();
    }

    /**
     * Создает нового пользователя на основе данных, введённых с консоли.
     */
    private void create() {
        String[] userInfo = readUserInfo();
        int age = parseIntFromConsole(userInfo[2].trim());
        userServiceImpl.save(new UserEntity(userInfo[0].trim(), userInfo[1].trim(), age, LocalDateTime.now()));
        System.out.println("Пользователь успешно создан!");
    }

    /**
     * Обновляет данные существующего пользователя, выбранного по ID.
     */
    private void update() {
        int id = chooseUserById();
        String[] userInfo = readUserInfo();
        int age = parseIntFromConsole(userInfo[2].trim());
        userServiceImpl.update(new UserEntity(id, userInfo[0].trim(), userInfo[1].trim(), age, userServiceImpl.findById(id).getCreatedAt()));
        System.out.println("Пользователь успешно обновлён!");
    }

    /**
     * Считывает данные пользователя из консоли в формате: "имя, email, возраст".
     *
     * @return массив строк, содержащий имя, email и возраст
     */
    private String[] readUserInfo() {
        System.out.println("Введите данные пользователя в формате:\n имя, email, возраст");
        String userInfo = scanner.nextLine();
        return userInfo.split(",");
    }

    /**
     * Удаляет пользователя, выбранного по ID.
     */
    private void delete() {
        int id = chooseUserById();
        userServiceImpl.delete(id);
        System.out.println("Пользователь успешно удалён!");
    }

    /**
     * Позволяет пользователю выбрать существующего пользователя по его ID.
     * При введении id, не совпадающего с id ни одного из существующих пользователей, сообщает об этом
     * и просит выбрать другой id.
     *
     * @return корректный идентификатор пользователя
     */
    private int chooseUserById() {
        System.out.println("Выберите пользователя и введите его id!");
        List<UserEntity> userEntities = userServiceImpl.getAll();
        showAll(userEntities);
        List<Integer> ids = userEntities.stream()
                .map(UserEntity::getId)
                .toList();
        int id = cleanNextInt();
        if (!ids.contains(id)) {
            System.out.println("Введённый id не соответствует ни одному из существующих пользователей!");
            return chooseUserById();
        }
        return id;
    }

    /**
     * Ищет пользователя по идентификатору и выводит результат на экран.
     */
    private void findById() {
        findBy(
                "id",
                id -> List.of(userServiceImpl.findById(parseIntFromConsole(id.trim())))
        );
    }

    /**
     * Ищет пользователей по имени и выводит результат на экран.
     */
    private void findByName() {
        findBy("имя", userServiceImpl::findByName);
    }

    /**
     * Ищет пользователя по адресу электронной почты и выводит результат на экран.
     */
    private void findByEmail() {
        findBy("email", email -> List.of(userServiceImpl.findByEmail(email)));
    }

    /**
     * Ищет пользователей по возрасту и выводит результат на экран.
     */
    private void findByAge() {
        findBy(
                "возраст",
                age -> userServiceImpl.findByAge(parseIntFromConsole(age.trim()))
        );
    }

    /**
     * Преобразует строку в число. Если введено некорректное значение — запрашивает ввод повторно.
     *
     * @param stringValue строковое представление введенного числа
     * @return корректное числовое значение введенного числа
     */
    private int parseIntFromConsole(String stringValue) {
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            System.out.println("Введите число еще раз.");
            return parseIntFromConsole(String.valueOf(cleanNextInt()));
        }
    }

    /**
     * Универсальный метод поиска пользователей по заданному параметру.
     *
     * @param parameterName  имя параметра (для отображения пользователю)
     * @param reposOperation функция, выполняющая поиск по введённому значению
     */
    private void findBy(String parameterName, Function<String, List<UserEntity>> reposOperation) {
        System.out.printf("Введите %s пользователя\n", parameterName);
        String parameter = scanner.nextLine();
        showAll(reposOperation.apply(parameter));
    }

    /**
     * Выводит список пользователей в консоль в читаемом виде.
     *
     * @param userEntities список пользователей для отображения
     */
    private void showAll(List<UserEntity> userEntities) {
        userEntities.stream()
                .map(user -> String.format(
                        "id: %s, name: %s, email: %s, age: %s, created at: %s",
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getAge(),
                        user.getCreatedAt()
                ))
                .forEach(System.out::println);
    }

    /**
     * Безопасно считывает целое число из консоли, очищая буфер от лишних символов.
     *
     * @return введённое пользователем целое число
     */
    private int cleanNextInt() {
        int value = scanner.nextInt();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        return value;
    }
}

