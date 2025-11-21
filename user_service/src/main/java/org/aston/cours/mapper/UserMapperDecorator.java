package org.aston.cours.mapper;

import org.aston.cours.dto.UserDto;
import org.aston.cours.entity.UserEntity;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.dto.UserOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public abstract class UserMapperDecorator implements UserMapper{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity convertToUser(UserDto dto, boolean setCreatedAt) {
        UserEntity entity = userMapper.convertToUser(dto, setCreatedAt);
        if (setCreatedAt) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        return entity;
    }

    @Override
    public UserDtoKafka convertToDtoKafka(UserEntity user, UserOperation operation) {
        UserDtoKafka userDtoKafka = userMapper.convertToDtoKafka(user, operation);
        userDtoKafka.setOperation(operation);
        return userDtoKafka;
    }
}
