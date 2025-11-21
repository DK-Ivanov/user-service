package org.aston.cours.hateoas.link;

import org.aston.cours.controller.UserController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Фабрика для создания HATEOAS-ссылок, связанных с ресурсом пользователя.
 * <p>
 * Данный компонент инкапсулирует генерацию всех навигационных ссылок,
 * указывающих на методы {@link UserController}.
 */
@Component
public class UserLinkFactory {

    /**
     * Создаёт ссылку на операцию создания пользователя.
     *
     * @return HATEOAS-ссылка с relation "create"
     */
    public Link create() {
        return linkTo(methodOn(UserController.class).create(null)).withRel("create");
    }

    /**
     * Создаёт ссылку на операцию обновления данных пользователя.
     *
     * @return HATEOAS-ссылка с relation "update"
     */
    public Link update() {
        return linkTo(methodOn(UserController.class).update(null)).withRel("update");
    }

    /**
     * Создаёт ссылку на операцию удаления пользователя.
     *
     * @return HATEOAS-ссылка с relation "delete"
     */
    public Link delete() {
        return linkTo(methodOn(UserController.class).delete(null)).withRel("delete");
    }

    /**
     * Создаёт ссылку на получение полного списка пользователей.
     *
     * @return HATEOAS-ссылка с relation "all"
     */
    public Link getAll() {
        return linkTo(methodOn(UserController.class).getAll()).withRel("all");
    }

    /**
     * Создаёт ссылку на поиск пользователей по имени.
     *
     * @return HATEOAS-ссылка с relation "by_name"
     */
    public Link byName() {
        return linkTo(methodOn(UserController.class).findByName(null)).withRel("by_name");
    }

    /**
     * Создаёт ссылку на поиск пользователей по возрасту.
     *
     * @return HATEOAS-ссылка с relation "by_age"
     */
    public Link byAge() {
        return linkTo(methodOn(UserController.class).findByAge(0)).withRel("by_age");
    }

    /**
     * Создаёт ссылку на поиск пользователя по email.
     *
     * @return HATEOAS-ссылка с relation "by_email"
     */
    public Link byEmail() {
        return linkTo(methodOn(UserController.class).findByEmail(null)).withRel("by_email");
    }
}
