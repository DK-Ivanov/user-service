package org.aston.cours.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Void> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header("Error-Message", "Пользователь не найден!")
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleOtherExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Error-Message", "Server error occurred")
                .build();
    }
}
