package org.aston.cours.controller;

import org.aston.cours.dto.UserDto;
import org.aston.cours.dto.UserMessageDto;
import org.aston.cours.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер, предоставляющий эндпоинты для отправки
 * уведомлений, связанных с пользователями.
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Отправляет уведомление о создании нового пользователя.
     *
     * <p>HTTP POST: <b>/notifications/user/created</b></p>
     *
     * @param userDto DTO с данными пользователя
     * @return HTTP 200 OK при успешной отправке сообщения
     */
    @PostMapping("/user/created")
    public ResponseEntity<Void> sendUserCreated(@RequestBody UserDto userDto) {
        notificationService.sendCreateMessage(userDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Отправляет уведомление об удалении пользователя.
     *
     * <p>HTTP POST: <b>/notifications/user/deleted</b></p>
     *
     * @param userDto DTO с идентификационными данными пользователя
     * @return HTTP 200 OK при успешной отправке сообщения
     */
    @PostMapping("/user/deleted")
    public ResponseEntity<Void> sendUserDeleted(@RequestBody UserDto userDto) {
        notificationService.sendDeleteMessage(userDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Отправляет произвольное пользовательское уведомление.
     *
     * <p>HTTP POST: <b>/notifications/custom</b></p>
     *
     * @param messageDto DTO с текстом произвольного сообщения, темой сообщения и email пользователя
     * @return HTTP 200 OK при успешной отправке сообщения
     */
    @PostMapping("/custom")
    public ResponseEntity<Void> sendCustom(@RequestBody UserMessageDto messageDto) {
        notificationService.sendCustomMessage(messageDto);
        return ResponseEntity.ok().build();
    }
}
