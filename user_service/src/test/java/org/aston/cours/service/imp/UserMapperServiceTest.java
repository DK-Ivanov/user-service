package org.aston.cours.service.imp;

import org.aston.cours.dto.UserDto;
import org.aston.cours.entity.UserEntity;
import org.aston.cours.mapper.UserMapperDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserMapperServiceTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserMapperDecorator mapper;

    @InjectMocks
    private UserMapperService userMapperService;

    @Captor
    private ArgumentCaptor<UserEntity> userCaptor;

    private UserEntity user = new UserEntity(
            1,
            "Dima",
            "qwerty123@ya.ru",
            12,
            LocalDateTime.now()
    );
    private UserDto dto;

    @BeforeEach
    void dtoInit() {
        dto = new UserDto();
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
    }

    @Test
    @DisplayName("Успешный маппинг пользователя при сохранении")
    public void testSaveSucces() {
        when(mapper.convertToUser(dto, true)).thenReturn(user);

        userMapperService.save(dto);

        verify(userService).save(userCaptor.capture());
        assertEquals(user, userCaptor.getValue());
    }

    @Test
    @DisplayName("Успешный маппинг пользователя при обновлении")
    public void testUpdateSucces() {
        when(mapper.convertToUser(dto, false)).thenReturn(user);

        userMapperService.update(dto);

        verify(userService).update(userCaptor.capture());
        assertEquals(user, userCaptor.getValue());
    }

    @Test
    @DisplayName("Успешный маппинг пользователя при удалении по ID")
    void testDeleteByIdSucces() {
        userMapperService.delete(1);

        verify(userService).delete(1);
    }

    @Test
    @DisplayName("Успешный маппинг пользователя при удалении по email")
    void testDeleteByEmailSucces() {
        userMapperService.delete("qwerty123@ya.ru");

        verify(userService).delete("qwerty123@ya.ru");
    }

    @Test
    @DisplayName("Успешный маппинг пользователей при получении всех")
    void testGetAllSucces() {
        when(userService.getAll()).thenReturn(List.of(user));
        when(mapper.convertToDto(user)).thenReturn(dto);

        List<UserDto> result = userMapperService.getAll();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(userService).getAll();
    }

    @Test
    @DisplayName("Успешный маппинг пользователя при поиске по ID")
    void testFindByIdSucces() {
        when(userService.findById(1)).thenReturn(user);
        when(mapper.convertToDto(user)).thenReturn(dto);

        UserDto result = userMapperService.findById(1);

        assertEquals(dto, result);
        verify(userService).findById(1);
    }

    @Test
    @DisplayName("Успешный маппинг пользователей при поиске по имени")
    void testFindByNameSucces() {
        when(userService.findByName("Dima")).thenReturn(List.of(user));
        when(mapper.convertToDto(user)).thenReturn(dto);

        List<UserDto> result = userMapperService.findByName("Dima");

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(userService).findByName("Dima");
    }

    @Test
    @DisplayName("Успешный маппинг пользователя при поиске по email")
    void testFindByEmailSucces() {
        when(userService.findByEmail("qwerty123@ya.ru")).thenReturn(user);
        when(mapper.convertToDto(user)).thenReturn(dto);

        UserDto result = userMapperService.findByEmail("qwerty123@ya.ru");

        assertEquals(dto, result);
        verify(userService).findByEmail("qwerty123@ya.ru");
    }

    @Test
    @DisplayName("Успешный маппинг пользователей при поиске по возрасту")
    void testFindByAgeSucces() {
        when(userService.findByAge(12)).thenReturn(List.of(user));
        when(mapper.convertToDto(user)).thenReturn(dto);

        List<UserDto> result = userMapperService.findByAge(12);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(userService).findByAge(12);
    }
}
