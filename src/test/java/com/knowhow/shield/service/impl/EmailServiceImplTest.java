package com.knowhow.shield.service.impl;

import com.knowhow.shield.config.AppProperties;
import com.knowhow.shield.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.mail.SimpleMailMessage;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @Spy
    AppProperties appProperties;

    @Spy
    SimpleMailMessage simpleMailMessage;

    @Mock
    Source source;

    EmailService emailService;


    @Before
    public void init() {
        emailService = new EmailServiceImpl(appProperties, simpleMailMessage, source);
    }

    @Test
    public void givenValidUserAndTokenExpectMailSent() {
//        //Arrange
//        User user = mock(User.class);
//        when(user.getFirstName()).thenReturn("Ahmed");
//        AppProperties.App app = new AppProperties.App();
//        app.setActivationDomainUrl("http://localhost:8080");
//        when(appProperties.getApp()).thenReturn(app);
//        MessageChannel channel  = mock(MessageChannel.class);
//        when(source.output()).thenReturn(channel);
//        when(channel.send(any())).thenReturn(true);
//        ActivationMessageDto dto = new ActivationMessageDto("Activation Code","","");
//
//        String token = "abc123";
//
//        //Act
//        ActivationMessageDto result =  emailService.sendActivationEmail(user, token);
//
//        //Assert
//        verify()
    }
}
