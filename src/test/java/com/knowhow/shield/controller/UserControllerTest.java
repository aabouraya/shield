package com.knowhow.shield.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.service.UserService;
import java.util.Arrays;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mvc;

    private JacksonTester<UserDto> jacksonTester;
    private UUID id;
    @Before
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
        id = UUID.randomUUID();
    }

    @Test
    public void givenPageableReturnAllUsers() throws Exception {
        //Arrange
        Page<UserDto> page = new PageImpl<>(Arrays.asList(new UserDto("mark", "alex", "mark.alex@mail.com", true)));
        doReturn(page).when(userService).getUsers(any());

        //Act
        ResultActions result = mvc.perform(get("/shield/users").contentType(MediaType.APPLICATION_JSON));

        //Assert
        result.andExpect(status().isOk()).andExpect(jsonPath("content[0].firstName", is("mark")))
                .andExpect(jsonPath("content[0].lastName", is("alex")))
                .andExpect(jsonPath("content[0].email", is("mark.alex@mail.com")))
                .andExpect(jsonPath("content[0].enabled", is(true)));

    }

    @Test
    public void givenUserDataExpectUpdateUser() throws Exception {
        //Arrange
        UserDto user = new UserDto("mark", "alex", "mark.alex@mail.com", true);
        doNothing().when(userService).updateUser(any(), any(UserDto.class));
        JacksonTester.initFields(this, new ObjectMapper());
        ArgumentCaptor<UserDto> userCapture = ArgumentCaptor.forClass(UserDto.class);

        //Act
        ResultActions result = mvc.perform(put("/shield/users/{id}", id).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jacksonTester.write(user).getJson()));

        //Assert
        result.andExpect(status().isNoContent());
        verify(userService, times(1)).updateUser(eq(id), userCapture.capture());
        assertThat(userCapture.getValue().getFirstName()).isEqualTo("mark");
        assertThat(userCapture.getValue().getLastName()).isEqualTo("alex");
        assertThat(userCapture.getValue().getEmail()).isEqualTo("mark.alex@mail.com");
        assertThat(userCapture.getValue().isEnabled()).isEqualTo(true);
    }

    @Test
    public void givenUserIdExpectDeleteUser() throws Exception {
        //Arrange
        doNothing().when(userService).deleteUser(id);

        //Act
        ResultActions result = mvc.perform(delete("/shield/users/{id}", id));

        //Assert
        result.andExpect(status().isNoContent());
        verify(userService, times(1)).deleteUser(id);
    }
}
