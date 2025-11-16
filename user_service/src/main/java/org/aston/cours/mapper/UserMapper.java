package org.aston.cours.mapper;

import org.aston.cours.dto.UserDto;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.entity.UserEntity;
import org.aston.cours.kafka.dto.UserOperation;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserEntity convertToUser(UserDto dto, boolean setCreatedAt);

    UserDto convertToDto(UserEntity user);

    @Mapping(target = "operation", ignore = true)
    UserDtoKafka convertToDtoKafka(UserEntity user, UserOperation operation);
}
