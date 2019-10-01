package com.knowhow.shield.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import com.knowhow.shield.service.ActivationService;
import com.knowhow.shield.service.EmailService;
import com.knowhow.shield.service.VerificationTokenService;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivationServiceImplTest {

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    private ActivationService activationService;

    @Before
    public void init() {
        activationService = new ActivationServiceImpl(verificationTokenService, emailService, userRepository);
    }

    @Test
    public void sendActivationEmailExpectCreateTokenAndSendEmail() {
        //Arrange
        User user = mock(User.class);
        doReturn("123456789").when(verificationTokenService).createVerificationToken(any(User.class));
        // doNothing().when(emailService).sendActivationEmail(any(User.class), anyString());

        //Act
        activationService.sendActivationEmail(user);

        //Assert
        verify(verificationTokenService, times(1)).createVerificationToken(any(User.class));
        verify(emailService, times(1)).sendActivationEmail(any(User.class), anyString());
    }

    @Test
    public void givenValidTokenExpectUserId() {
        //Arrange
        User user = mock(User.class);
        UUID id = UUID.randomUUID();
        when(user.getId()).thenReturn(id);
        when(verificationTokenService.findUserByToken("1234")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        doNothing().when(verificationTokenService).deactivateToken("1234");

        //Act
        UUID result = activationService.activateUser("1234");

        //Assert
        assertThat(result).isEqualTo(id);
        verify(userRepository, times(1)).save(user);
        verify(user, times(1)).setEnabled(true);
        verify(verificationTokenService, times(1)).deactivateToken("1234");

    }
}
