package org.aston.cours.kafka.dto;

/**
 * Описывает возможные операции, выполняемые над пользователем.
 */
public enum UserOperation {
    /**
     * Создание нового пользователя.
     */
    CREATE,

    /**
     * Удаление существующего пользователя.
     */
    DELETE;
}
