package org.aston.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.cours.controller.UserController;
import org.aston.cours.dto.UserDto;
import org.aston.cours.entity.UserEntity;
import org.aston.cours.exception.ApplicationControllerExceptionHandler;
import org.aston.cours.exception.UserNotFoundException;
import org.aston.cours.mapper.UserMapper;
import org.aston.cours.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = org.aston.cours.Runner.class)
@Import(ApplicationControllerExceptionHandler.class)
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc userController;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    private UserEntity user = new UserEntity(1, "Dima", "qwerty1yaru", 18, LocalDateTime.now());
    private UserDto dto = new UserDto();

    @BeforeAll
    void dtoInit() {
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
    }

    @Test
    @DisplayName("Успешное создание пользователя")
    void testCreateUserSucces() throws Exception {
        userController.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Успешное обновление пользователя")
    void testUpdateUserSucces() throws Exception {
        userController.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Успешное удаление пользователя")
    void testDeleteByEmailSucces() throws Exception {
        userController.perform(delete("/users/{email}", dto.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(userService).delete(dto.getEmail());
    }

    @Test
    @DisplayName("Успешное получение всех пользователей")
    void testGetAllUsersSucces() throws Exception {
        when(userService.getAll()).thenReturn(List.of(user));
        when(userMapper.convertToDto(user)).thenReturn(dto);

        userController.perform(get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(dto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(dto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(dto.getAge()));
        verify(userService).getAll();
        verify(userMapper).convertToDto(user);
    }

    @Test
    @DisplayName("Успешное получение пользователей по имени")
    void testGetUserByNameSucces() throws Exception {
        when(userService.findByName(user.getName())).thenReturn(List.of(user));
        when(userMapper.convertToDto(user)).thenReturn(dto);

        userController.perform(get("/users/by-name/{name}", dto.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(dto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(dto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(dto.getAge()));
        verify(userService).findByName(dto.getName());
        verify(userMapper).convertToDto(user);
    }

    @Test
    @DisplayName("Успешное получение пользователей по email")
    void testGetUserByEmailSucces() throws Exception {
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(userMapper.convertToDto(user)).thenReturn(dto);

        userController.perform(get("/users/by-email/{email}", dto.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(dto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(dto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(dto.getAge()));
        verify(userService).findByEmail(dto.getEmail());
        verify(userMapper).convertToDto(user);
    }

    @Test
    @DisplayName("Пользователь с таким email не найден")
    void testFindByEmailUserNotFound() throws Exception {
        when(userService.findByEmail(dto.getEmail()))
                .thenThrow(new UserNotFoundException());

        userController.perform(get("/users/by-email/{email}", dto.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.header()
                        .stringValues("Error-Message", "Пользователь не найден!")
                );
    }

    @Test
    @DisplayName("Успешное получение пользователей по возрасту")
    void testGetUserByAgeSucces() throws Exception {
        when(userService.findByAge(user.getAge())).thenReturn(List.of(user));
        when(userMapper.convertToDto(user)).thenReturn(dto);

        userController.perform(get("/users/by-age/{age}", dto.getAge()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(dto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(dto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(dto.getAge()));
        verify(userService).findByAge(dto.getAge());
        verify(userMapper).convertToDto(user);
    }
}
