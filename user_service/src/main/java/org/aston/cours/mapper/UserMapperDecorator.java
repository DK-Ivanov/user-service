package org.aston.cours.mapper;

import org.aston.cours.dto.UserDto;
import org.aston.cours.entity.UserEntity;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.dto.UserOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    @Qualifier("delegate")
    private UserMapper userMapper;

    @Override
    public UserEntity entityOfUserDto(UserDto dto, boolean setCreatedAt) {
        UserEntity entity = userMapper.entityOfUserDto(dto, setCreatedAt);

        return setCreatedAt ? setTimeNow(entity) : entity;
    }

    private UserEntity setTimeNow(UserEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }

    @Override
    public UserDtoKafka dtoKafkaOfEntity(UserEntity user, UserOperation operation) {
        UserDtoKafka userDtoKafka = userMapper.dtoKafkaOfEntity(user, operation);
        userDtoKafka.setOperation(operation);

        return userDtoKafka;
    }
}
