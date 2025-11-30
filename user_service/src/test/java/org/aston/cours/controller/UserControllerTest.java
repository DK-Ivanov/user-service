package org.aston.cours.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.cours.UserServiceRunner;
import org.aston.cours.dto.UserDto;
import org.aston.cours.exception.ApplicationControllerExceptionHandler;
import org.aston.cours.exception.UserNotFoundException;
import org.aston.cours.hateoas.assembler.UserModelAssembler;
import org.aston.cours.hateoas.model.UserModel;
import org.aston.cours.service.EntityService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = UserServiceRunner.class)
@Import(ApplicationControllerExceptionHandler.class)
public class UserControllerTest {

    @MockBean
    private EntityService<UserDto> entityService;

    @MockBean
    private UserModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc userController;

    private UserDto dto = new UserDto();

    private RepresentationModel<?> operationModel;
    private List<Link> hateoasLinks = new ArrayList<>();

    @BeforeAll
    void dtoInit() {
        dto.setName("Dima");
        dto.setAge(18);
        dto.setEmail("qwerty1yaru");

        // Формируем тестовые ссылки
        hateoasLinks = List.of(
                Link.of("/users/new").withRel("create"),
                Link.of("/users").withRel("update"),
                Link.of("/users/{email}").withRel("delete"),
                Link.of("/users").withRel("all"),
                Link.of("/users/by-name/{name}").withRel("by_name"),
                Link.of("/users/by-email/{email}").withRel("by_email"),
                Link.of("/users/by-age/{age}").withRel("by_age")
        );

        operationModel = new RepresentationModel<>();
        operationModel.add(hateoasLinks);
    }

    @Test
    @DisplayName("Успешное создание пользователя")
    void testCreateUserSucces() throws Exception {
        when(assembler.toOperationModel()).thenReturn((RepresentationModel) operationModel);

        userController.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    @DisplayName("Успешное обновление пользователя")
    void testUpdateUserSucces() throws Exception {
        when(assembler.toOperationModel()).thenReturn((RepresentationModel) operationModel);

        userController.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    @DisplayName("Успешное удаление пользователя")
    void testDeleteByEmailSucces() throws Exception {
        when(assembler.toOperationModel()).thenReturn((RepresentationModel) operationModel);

        userController.perform(delete("/users/{email}", dto.getEmail()))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$._links").exists());

        verify(entityService).delete(dto.getEmail());
    }

    @Test
    @DisplayName("Успешное получение всех пользователей")
    void testGetAllUsersSucces() throws Exception {
        when(entityService.getAll()).thenReturn(List.of(dto));

        CollectionModel<UserModel> model = CollectionModel.empty();
        model.add(Link.of("/users").withRel("all"));
        when(assembler.toCollectionModel(any())).thenReturn(model);

        userController.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").exists());

        verify(entityService).getAll();
    }

    @Test
    @DisplayName("Успешное получение пользователей по имени")
    void testGetUserByNameSucces() throws Exception {
        when(entityService.findByName(dto.getName())).thenReturn(List.of(dto));

        CollectionModel<UserModel> model = CollectionModel.empty();
        model.add(Link.of("/users").withRel("all"));
        when(assembler.toCollectionModel(any())).thenReturn(model);

        userController.perform(get("/users/by-name/{name}", dto.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").exists());

        verify(entityService).findByName(dto.getName());
    }

    @Test
    @DisplayName("Успешное получение пользователей по email")
    void testGetUserByEmailSucces() throws Exception {
        when(entityService.findByEmail(dto.getEmail())).thenReturn(dto);

        UserModel userModel = new UserModel(dto);
        userModel.add(Link.of("/users/new").withRel("create"));
        when(assembler.toModel(any())).thenReturn(userModel);

        userController.perform(get("/users/by-email/{email}", dto.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").exists());

        verify(entityService).findByEmail(dto.getEmail());
    }

    @Test
    @DisplayName("Пользователь с таким email не найден")
    void testFindByEmailUserNotFound() throws Exception {
        when(entityService.findByEmail(dto.getEmail()))
                .thenThrow(new UserNotFoundException());

        userController.perform(get("/users/by-email/{email}", dto.getEmail()))
                .andExpect(status().isNotFound())
                .andExpect(header().stringValues("Error-Message", "Пользователь не найден!"));
    }

    @Test
    @DisplayName("Успешное получение пользователей по возрасту")
    void testGetUserByAgeSucces() throws Exception {
        when(entityService.findByAge(dto.getAge())).thenReturn(List.of(dto));

        CollectionModel<UserModel> model = CollectionModel.empty();
        model.add(Link.of("/users").withRel("all"));
        when(assembler.toCollectionModel(any())).thenReturn(model);

        userController.perform(get("/users/by-age/{age}", dto.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").exists());

        verify(entityService).findByAge(dto.getAge());
    }
}
