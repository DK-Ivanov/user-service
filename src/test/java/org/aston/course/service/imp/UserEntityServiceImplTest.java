package org.aston.course.service.imp;

import org.aston.cours.entity.UserEntity;
import org.aston.cours.repository.UserRepository;
import org.aston.cours.service.imp.UserServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<UserEntity> userCaptor;

    private UserEntity userEntity = new UserEntity(
            1,
            "Dima",
            "qwerty123@ya.ru",
            12,
            LocalDateTime.now()
    );

    @Test
    @DisplayName("Сохраняет пользователя в бд через UserRepository")
    public void testUserCreateSucces() {
        userService.save(userEntity);

        verify(userRepository).save(userCaptor.capture());
        assertEquals(userEntity.getId(), userCaptor.getValue().getId());
    }

    @Test
    @DisplayName("Обновляет пользователя в бд через UserRepository")
    public void testUpdateSucces() {
        userService.update(userEntity);

        verify(userRepository).save(userCaptor.capture());
        assertEquals(userEntity.getId(), userCaptor.getValue().getId());
    }

    @Test
    @DisplayName("Удаляет пользователя по id из бд через UserRepository")
    public void testDeleteByIdSucces() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        userService.delete(1);

        verify(userRepository).delete(userCaptor.capture());
        assertEquals(userEntity, userCaptor.getValue());
    }

    @Test
    @DisplayName("Удаляет пользователя по email из бд через UserRepository")
    public void testDeleteByEmailSucces() {
        userService.delete(userEntity.getEmail());

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);

        verify(userRepository).deleteByEmail(emailCaptor.capture());
        assertEquals(userEntity.getEmail(), emailCaptor.getValue());
    }

    @Test
    @DisplayName("Получает всех пользователей из бд через UserRepository")
    public void testGetAllReturnAllUsersSucces() {
        UserEntity userEntity2 = new UserEntity(
                2,
                "Антон",
                "ytrewq321@gmail.com",
                15,
                LocalDateTime.now()
        );
        List<UserEntity> allUserEntities = List.of(userEntity, userEntity2);
        when(userRepository.findAll()).thenReturn(allUserEntities);

        List<UserEntity> result = userService.getAll();

        assertEquals(allUserEntities, result);
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Получает пользователя по id из бд через UserRepository")
    public void testFindByIdSucces() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.findById(1);

        assertEquals(userEntity, result);
    }

    @Test
    @DisplayName("Получает пользователя по имени из бд через UserRepository")
    public void testFindByNameSucces() {
        when(userRepository.findByName("Dima")).thenReturn(List.of(userEntity));

        List<UserEntity> result = userService.findByName("Dima");

        assertEquals(result, List.of(userEntity));
        verify(userRepository).findByName("Dima");
    }

    @Test
    @DisplayName("Получает пользователя по возрасту из бд через UserRepository")
    public void testFindByAgeSucces() {
        when(userRepository.findByAge(12)).thenReturn(List.of(userEntity));

        List<UserEntity> result = userService.findByAge(12);

        assertEquals(List.of(userEntity), result);
        verify(userRepository).findByAge(12);
    }

    @Test
    @DisplayName("Получает пользователя по email из бд через UserRepository")
    public void testFindByEmailSucces() {
        when(userRepository.findByEmail("qwerty123@ya.ru")).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.findByEmail("qwerty123@ya.ru");

        assertEquals(userEntity, result);
        verify(userRepository).findByEmail("qwerty123@ya.ru");
    }
}
