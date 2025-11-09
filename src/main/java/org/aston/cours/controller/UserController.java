package org.aston.cours.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aston.cours.dto.UserDto;
import org.aston.cours.mapper.UserMapper;
import org.aston.cours.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-контроллер для управления пользователями.
 * Предоставляет CRUD-операции класса User.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "UserController", description = "методы для работы с сущностью User")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Создает нового пользователя.
     *
     * @param dto объект UserDto с данными пользователя
     * @return ResponseEntity с HTTP статусом 201 CREATED
     */
    @Operation(summary = "Создать нового пользователя")
    @PostMapping("/new")
    public ResponseEntity<Void> create(@RequestBody UserDto dto) {
        userService.save(userMapper.convertToUser(dto, true));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param dto объект UserDto с обновленными данными пользователя
     * @return ResponseEntity с HTTP статусом 204 NO_CONTENT
     */
    @Operation(summary = "Обновить данные пользователя")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserDto dto) {
        userService.update(userMapper.convertToUser(dto, false));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Удаляет пользователя по email.
     *
     * @param email email пользователя, которого нужно удалить
     * @return ResponseEntity с HTTP статусом 204 NO_CONTENT
     */
    @Operation(summary = "Удалить пользователя по email")
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        userService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return ResponseEntity с HTTP статусом 200 OK и списком UserDto
     */
    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream()
                .map(userMapper::convertToDto)
                .toList()
        );
    }

    /**
     * Находит пользователей по имени.
     *
     * @param name имя пользователя
     * @return ResponseEntity с HTTP статусом 200 OK и списком UserDto
     */
    @Operation(summary = "Найти пользователей по имени")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<UserDto>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.findByName(name).stream()
                .map(userMapper::convertToDto)
                .toList()
        );
    }

    /**
     * Находит пользователя по email.
     *
     * @param email email пользователя
     * @return ResponseEntity с HTTP статусом 200 OK и объектом UserDto
     */
    @Operation(summary = "Найти пользователя по email")
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userMapper.convertToDto(userService.findByEmail(email)));
    }

    /**
     * Находит пользователей по возрасту.
     *
     * @param age возраст пользователя
     * @return ResponseEntity с HTTP статусом 200 OK и списком UserDto
     */
    @Operation(summary = "Найти пользователей по возрасту")
    @GetMapping("/by-age/{age}")
    public ResponseEntity<List<UserDto>> findByAge(@PathVariable int age) {
        return ResponseEntity.ok(userService.findByAge(age).stream()
                .map(userMapper::convertToDto)
                .toList()
        );
    }
}
