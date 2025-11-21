package org.aston.cours.kafka.service.imp;

import org.aston.cours.entity.UserEntity;
import org.aston.cours.kafka.dto.UserDtoKafka;
import org.aston.cours.kafka.dto.UserOperation;
import org.aston.cours.kafka.producer.UserKafkaProducer;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserKafkaServiceImplTest {

    @Mock
    private UserKafkaProducer userKafkaProducer;
    @Mock
    private UserMapperDecorator userMapperDecorator;

    @InjectMocks
    private UserKafkaServiceImpl userKafkaService;

    @Captor
    private ArgumentCaptor<UserEntity> userCapture;
    @Captor
    private ArgumentCaptor<UserDtoKafka> userDtoKafkaCaptor;
    @Captor
    private ArgumentCaptor<UserOperation> userOperationCaptor;

    private UserEntity user = new UserEntity(
            "Dima",
            "qwerty123@ya.ru",
            18,
            LocalDateTime.now()
    );

    private UserDtoKafka userDtoKafka;

    @BeforeEach
    public void reset() {
        userDtoKafka = new UserDtoKafka(user.getName(), user.getEmail());
    }

    @Test
    @DisplayName("Успешное сообщение о создании")
    public void testCreateSucces() {
        userDtoKafka.setOperation(UserOperation.CREATE);
        when(userMapperDecorator.convertToDtoKafka(user, UserOperation.CREATE)).thenReturn(userDtoKafka);

        userKafkaService.create(user);

        verify(userMapperDecorator).convertToDtoKafka(userCapture.capture(), userOperationCaptor.capture());
        verify(userKafkaProducer).sendUserToKafka(userDtoKafkaCaptor.capture());
        assertEquals(user, userCapture.getValue());
        assertEquals(UserOperation.CREATE, userOperationCaptor.getValue());
        assertEquals(userDtoKafka, userDtoKafkaCaptor.getValue());
    }

    @Test
    @DisplayName("Успешное уведомление об удалении")
    public void testDeleteSucces() {
        userDtoKafka.setOperation(UserOperation.DELETE);
        when(userMapperDecorator.convertToDtoKafka(user, UserOperation.DELETE)).thenReturn(userDtoKafka);

        userKafkaService.delete(user);

        verify(userMapperDecorator).convertToDtoKafka(userCapture.capture(), userOperationCaptor.capture());
        verify(userKafkaProducer).sendUserToKafka(userDtoKafkaCaptor.capture());
        assertEquals(user, userCapture.getValue());
        assertEquals(UserOperation.DELETE, userOperationCaptor.getValue());
        assertEquals(userDtoKafka, userDtoKafkaCaptor.getValue());
    }
}