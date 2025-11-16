package org.aston.cours.kafka.service.imp;

import org.aston.cours.dto.UserDto;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.dto.UserOperation;
import org.aston.cours.mapper.UserMapper;
import org.aston.cours.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserKafkaNotificationHandleServiceTest {

    @Mock
    private NotificationService notificationService;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserKafkaNotificationHandleService userService;

    @Captor
    private ArgumentCaptor<UserDtoKafka> userCapture;
    @Captor
    private ArgumentCaptor<UserDto> dtoCapture;

    private UserDtoKafka user;
    private UserDto dto = new UserDto("Dima", "qwerty123@ya.ru");

    @BeforeEach
    public void createUser() {
        user = new UserDtoKafka();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
    }

    @Test
    @DisplayName("Успешная обработка сообщения о создании пользователя")
    void handleCreateMessageSucces() {
        user.setOperation(UserOperation.CREATE);
        when(userMapper.convertToUserDto(user)).thenReturn(dto);

        userService.handleMessage(user);

        verify(userMapper).convertToUserDto(userCapture.capture());
        verify(notificationService).sendCreateMessage(dtoCapture.capture());
        assertEquals(user, userCapture.getValue());
        assertEquals(dto, dtoCapture.getValue());
    }

    @Test
    @DisplayName("Успешная обработка сообщения об удалении пользователя")
    void handleDeleteMessageSucces() {
        user.setOperation(UserOperation.DELETE);
        when(userMapper.convertToUserDto(user)).thenReturn(dto);

        userService.handleMessage(user);

        verify(userMapper).convertToUserDto(userCapture.capture());
        verify(notificationService).sendDeleteMessage(dtoCapture.capture());
        assertEquals(user, userCapture.getValue());
        assertEquals(dto, dtoCapture.getValue());
    }
}