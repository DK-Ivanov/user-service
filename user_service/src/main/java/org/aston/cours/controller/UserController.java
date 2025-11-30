package org.aston.cours.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aston.cours.dto.UserDto;
import org.aston.cours.hateoas.assembler.UserModelAssembler;
import org.aston.cours.hateoas.model.UserModel;
import org.aston.cours.service.EntityService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
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

/**
 * REST-контроллер для управления пользователями.
 * Предоставляет CRUD-операции класса User.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "UserController", description = "методы для работы с сущностью User")
public class UserController {
    private final EntityService<UserDto> entityService;
    private final UserModelAssembler assembler;

    public UserController(EntityService<UserDto> entityService, UserModelAssembler assembler) {
        this.entityService = entityService;
        this.assembler = assembler;
    }

    /**
     * Создаёт нового пользователя и возвращает HATEOAS-ссылки для дальнейшей навигации.
     *
     * @param dto данные нового пользователя
     * @return ResponseEntity со статусом 201 CREATED и моделью с HATEOAS-ссылками
     */
    @Operation(summary = "Создать нового пользователя")
    @PostMapping("/new")
    public ResponseEntity<RepresentationModel<?>> create(@RequestBody UserDto dto) {
        entityService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toOperationModel());
    }

    /**
     * Обновляет данные существующего пользователя и возвращает HATEOAS-ссылки
     * для дальнейших действий.
     *
     * @param dto обновлённые данные пользователя
     * @return ResponseEntity со статусом 204 NO_CONTENT и моделью с HATEOAS-ссылками
     */
    @Operation(summary = "Обновить данные пользователя")
    @PutMapping
    public ResponseEntity<RepresentationModel<?>> update(@RequestBody UserDto dto) {
        entityService.update(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(assembler.toOperationModel());
    }

    /**
     * Удаляет пользователя по email и возвращает HATEOAS-ссылки.
     *
     * @param email email пользователя для удаления
     * @return ResponseEntity со статусом 204 NO_CONTENT и моделью с HATEOAS-ссылками
     */
    @Operation(summary = "Удалить пользователя по email")
    @DeleteMapping("/{email}")
    public ResponseEntity<RepresentationModel<?>> delete(@PathVariable String email) {
        entityService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(assembler.toOperationModel());
    }


    /**
     * Возвращает список всех пользователей с HATEOAS-ссылками.
     *
     * @return ResponseEntity со статусом 200 OK и коллекцией моделей пользователей
     */
    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public ResponseEntity<CollectionModel<UserModel>> getAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(entityService.getAll()));
    }

    /**
     * Ищет пользователей по имени и возвращает результат с HATEOAS-ссылками.
     *
     * @param name имя пользователя
     * @return ResponseEntity со статусом 200 OK и коллекцией найденных пользователей
     */
    @Operation(summary = "Найти пользователей по имени")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<CollectionModel<UserModel>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(entityService.findByName(name)));
    }

    /**
     * Находит пользователя по email и возвращает модель с HATEOAS-ссылками.
     *
     * @param email email пользователя
     * @return ResponseEntity со статусом 200 OK и моделью пользователя
     */
    @Operation(summary = "Найти пользователя по email")
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserModel> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(assembler.toModel(entityService.findByEmail(email)));
    }

    /**
     * Ищет пользователей по возрасту и возвращает результат с HATEOAS-ссылками.
     *
     * @param age возраст пользователя
     * @return ResponseEntity со статусом 200 OK и коллекцией найденных пользователей
     */
    @Operation(summary = "Найти пользователей по возрасту")
    @GetMapping("/by-age/{age}")
    public ResponseEntity<CollectionModel<UserModel>> findByAge(@PathVariable int age) {
        return ResponseEntity.ok(assembler.toCollectionModel(entityService.findByAge(age)));
    }
}
