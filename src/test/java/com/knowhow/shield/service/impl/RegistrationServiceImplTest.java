package com.knowhow.shield.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.event.CompleteRegistrationEvent;
import com.knowhow.shield.model.User;
import com.knowhow.shield.service.RegistrationService;
import com.knowhow.shield.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private ApplicationEventPublisher publisher;

    private RegistrationService registrationService;

    @Before
    public void setUp() {
        registrationService = new RegistrationServiceImpl(userService, publisher);
    }

    @Test
    public void givenValidRegistrationDtoExpectUserId() {
        //Arrange
        RegistrationDto registrationDto = new RegistrationDto("Ahmed", "Aly", "abc123", "abc123", "a.a@gmail.com");
        User user = mock(User.class);
        when(userService.createUser(any())).thenReturn(user);
        doNothing().when(publisher).publishEvent(any(CompleteRegistrationEvent.class));

        //Act
        registrationService.register(registrationDto);

        //Assert
        verify(publisher, times(1)).publishEvent(any(CompleteRegistrationEvent.class));
        verify(userService, times(1)).createUser(any());

    }
}
