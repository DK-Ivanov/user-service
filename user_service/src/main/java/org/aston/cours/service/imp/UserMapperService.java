package org.aston.cours.service.imp;

import org.aston.cours.dto.UserDto;
import org.aston.cours.mapper.UserMapperDecorator;
import org.aston.cours.service.EntityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapperService implements EntityService<UserDto> {

    private final UserMapperDecorator userMapper;
    private final UserServiceImpl userService;

    public UserMapperService(UserMapperDecorator userMapper, UserServiceImpl userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public void save(UserDto dto) {
        userService.save(userMapper.convertToUser(dto, true));
    }

    @Override
    public void update(UserDto dto) {
        userService.update(userMapper.convertToUser(dto, false));
    }

    @Override
    public void delete(int id) {
        userService.delete(id);
    }

    @Override
    public void delete(String email) {
        userService.delete(email);
    }

    @Override
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(userMapper::convertToDto)
                .toList();
    }

    @Override
    public UserDto findById(int id) {
        return userMapper.convertToDto(userService.findById(id));
    }

    @Override
    public List<UserDto> findByName(String name) {
        return userService.findByName(name).stream()
                .map(userMapper::convertToDto)
                .toList();
    }

    @Override
    public UserDto findByEmail(String email) {
        return userMapper.convertToDto(userService.findByEmail(email));
    }

    @Override
    public List<UserDto> findByAge(int age) {
        return userService.findByAge(age).stream()
                .map(userMapper::convertToDto)
                .toList();
    }
}
