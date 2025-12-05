package org.aston.cours.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь создан успешно",
                    content = @Content(schema = @Schema(implementation = RepresentationModel.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping("/new")
    public ResponseEntity<RepresentationModel<?>> create(
            @Parameter(description = "DTO с данными нового пользователя", required = true,
                    schema = @Schema(implementation = UserDto.class))
            @RequestBody
            UserDto dto) {
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно обновлён",
                    content = @Content(schema = @Schema(implementation = RepresentationModel.class))),
            @ApiResponse(responseCode = "500", description = "Server error occurred")
    })
    @PutMapping
    public ResponseEntity<RepresentationModel<?>> update(
            @Parameter(description = "DTO с обновлёнными данными пользователя", required = true,
                    schema = @Schema(implementation = UserDto.class))
            @RequestBody
            UserDto dto) {
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён",
                    content = @Content(schema = @Schema(implementation = RepresentationModel.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<RepresentationModel<?>> delete(
            @Parameter(description = "Email пользователя для удаления", required = true)
            @PathVariable
            String email) {
        entityService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(assembler.toOperationModel());
    }


    /**
     * Возвращает список всех пользователей с HATEOAS-ссылками.
     *
     * @return ResponseEntity со статусом 200 OK и коллекцией моделей пользователей
     */
    @Operation(summary = "Получить список всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен",
                    content = @Content(schema = @Schema(implementation = CollectionModel.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователи успешно найдены",
                    content = @Content(schema = @Schema(implementation = CollectionModel.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/by-name/{name}")
    public ResponseEntity<CollectionModel<UserModel>> findByName(
            @Parameter(description = "Имя пользователя для поиска", required = true)
            @PathVariable
            String name) {
        return ResponseEntity.ok(assembler.toCollectionModel(entityService.findByName(name)));
    }

    /**
     * Находит пользователя по email и возвращает модель с HATEOAS-ссылками.
     *
     * @param email email пользователя
     * @return ResponseEntity со статусом 200 OK и моделью пользователя
     */
    @Operation(summary = "Найти пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно найден",
                    content = @Content(schema = @Schema(implementation = UserModel.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserModel> findByEmail(
            @Parameter(description = "Email пользователя для поиска", required = true)
            @PathVariable
            String email) {
        return ResponseEntity.ok(assembler.toModel(entityService.findByEmail(email)));
    }

    /**
     * Ищет пользователей по возрасту и возвращает результат с HATEOAS-ссылками.
     *
     * @param age возраст пользователя
     * @return ResponseEntity со статусом 200 OK и коллекцией найденных пользователей
     */
    @Operation(summary = "Найти пользователей по возрасту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователи успешно найдены",
                    content = @Content(schema = @Schema(implementation = CollectionModel.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/by-age/{age}")
    public ResponseEntity<CollectionModel<UserModel>> findByAge(
            @Parameter(description = "Возраст пользователей для поиска", required = true)
            @PathVariable
            int age) {
        return ResponseEntity.ok(assembler.toCollectionModel(entityService.findByAge(age)));
    }
}
