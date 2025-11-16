package org.aston.cours.mapper;

import org.aston.cours.dto.UserDto;
import org.aston.cours.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(getCreatedAt(dto, setCreatedAt))")
    UserEntity convertToUser(UserDto dto, @Context boolean setCreatedAt);

    default LocalDateTime getCreatedAt(UserDto dto, boolean setCreatedAt) {
        return setCreatedAt ? LocalDateTime.now() : null;
    }

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "age", source = "age")
    UserDto convertToDto(UserEntity user);
}
