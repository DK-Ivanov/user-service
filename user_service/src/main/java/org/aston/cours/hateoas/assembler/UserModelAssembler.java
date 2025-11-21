package org.aston.cours.hateoas.assembler;

import org.aston.cours.dto.UserDto;
import org.aston.cours.hateoas.link.UserLinkFactory;
import org.aston.cours.hateoas.model.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembler, отвечающий за преобразование DTO пользователя в HATEOAS-модели.
 * <p>
 * Данный класс инкапсулирует логику формирования гипермедиа-ссылок.
 * Все ссылки создаются через {@link UserLinkFactory}, чтобы обеспечить
 * единое централизованное управление навигацией внутри API.
 */
@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, UserModel> {

    private final UserLinkFactory linkFactory;

    public UserModelAssembler(UserLinkFactory linkFactory) {
        this.linkFactory = linkFactory;
    }

    /**
     * Создаёт пустую HATEOAS-модель, содержащую только навигационные ссылки.
     *
     * @return пустая {@link RepresentationModel} с набором ссылок для дальнейших действий
     */
    public RepresentationModel<?> toOperationModel() {
        RepresentationModel<?> model = new RepresentationModel<>();

        model.add(linkFactory.create());
        model.add(linkFactory.update());
        model.add(linkFactory.delete());
        model.add(linkFactory.getAll());
        model.add(linkFactory.byName());
        model.add(linkFactory.byEmail());
        model.add(linkFactory.byAge());

        return model;
    }

    /**
     * Преобразует объект {@link UserDto} в полноформатную HATEOAS-модель {@link UserModel}.
     * <p>
     * Помимо данных пользователя, модель содержит ссылки на все основные операции API.
     *
     * @param dto DTO пользователя
     * @return {@link UserModel} с данными пользователя и гипермедиа-ссылками
     */
    @Override
    public UserModel toModel(UserDto dto) {
        UserModel userModel = new UserModel(dto);

        userModel.add(linkFactory.create());
        userModel.add(linkFactory.update());
        userModel.add(linkFactory.delete());
        userModel.add(linkFactory.getAll());
        userModel.add(linkFactory.byName());
        userModel.add(linkFactory.byEmail());
        userModel.add(linkFactory.byAge());

        return userModel;
    }

    /**
     * Преобразует коллекцию DTO пользователей в {@link CollectionModel},
     * добавляя общий набор ссылок, относящийся к списку ресурсов.
     *
     * @param dtos коллекция объектов {@link UserDto}
     * @return {@link CollectionModel} содержащая модели пользователей и навигационные ссылки
     */
    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends UserDto> dtos) {
        CollectionModel<UserModel> models = RepresentationModelAssembler.super.toCollectionModel(dtos);

        models.add(linkFactory.getAll());
        models.add(linkFactory.create());

        return models;
    }
}
