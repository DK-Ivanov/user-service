package org.aston.cours.mapper;

import org.aston.cours.dto.UserDto;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto convertToUserDto(UserDtoKafka dto);
}
