package com.knowhow.shield.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knowhow.shield.config.AppProperties;
import com.knowhow.shield.model.User;
import com.knowhow.shield.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @Spy
    AppProperties appProperties;

    @Mock
    JavaMailSender mailSender;

    @Spy
    SimpleMailMessage simpleMailMessage;

    EmailService emailService;

    @Before
    public void init() {
        emailService = new EmailServiceImpl(appProperties, mailSender, simpleMailMessage);
    }

    @Test
    public void givenValidUserAndTokenExpectMailSent() {
        //Arrange
        User user = mock(User.class);
        when(user.getFirstName()).thenReturn("Ahmed");
        AppProperties.App app = new AppProperties.App();
        app.setActivationDomainUrl("http://localhost:8080");
        when(appProperties.getApp()).thenReturn(app);
        when(simpleMailMessage.getText()).thenReturn("%s %s %s");
        String token = "abc123";

        //Act
        emailService.sendActivationEmail(user, token);

        //Assert
        verify(mailSender, times(1)).send(simpleMailMessage);
    }
}