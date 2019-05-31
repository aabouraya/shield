package com.knowhow.shield.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.service.ActivationService;
import com.knowhow.shield.service.RegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    private MockMvc mvc;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private ActivationService activationService;

    @InjectMocks
    private RegistrationController registrationController;

    private JacksonTester<RegistrationDto> jsonRegistrationDto;

    @Before
    public void init() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void givenRegistrationExpectUserId() throws Exception {
        //Arrange
        RegistrationDto registrationDto = new RegistrationDto("ahmed", "abc", "abc123", "abc123", "abc123@mail.com");
        doReturn(1L).when(registrationService).register(any());

        //Act
        MockHttpServletResponse response = mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRegistrationDto.write(registrationDto).getJson())).andReturn().getResponse();

        //Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("1");
    }

    @Test
    public void givenValidActivationTokenExpectUserId() throws Exception {
        //Arrange
        doReturn(30L).when(activationService).activateUser("1234");

        //Act
        MockHttpServletResponse response = mvc.perform(get("/activation/1234")).andReturn().getResponse();

        //Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("30");
    }
}
