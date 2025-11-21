package org.aston.cours.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.cours.dto.UserDto;
import org.aston.cours.dto.UserMessageDto;
import org.aston.cours.service.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(NotificationController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = org.aston.cours.Runner.class)
class NotificationControllerTest {

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc controller;

    private UserDto dto = new UserDto("Dima", "qwerty123@gmail.com");

    @Test
    @DisplayName("Успешная отправка уведомления о создании пользователя")
    void testSendUserCreatedSucces() throws Exception {
        controller.perform(
                post("/notifications/user/created")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Успешная отправка уведомления об удалении пользователя")
    void testSendUserDeletedSucces() throws Exception {
        controller.perform(
                post("/notifications/user/deleted")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Успешная отправка кастомного уведомления")
    void testSendCustomMessageSucces() throws Exception {
        controller.perform(
                post("/notifications/custom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UserMessageDto(
                                        dto.getEmail(),
                                        "test",
                                        "hello world!"
                                )
                        ))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}