package com.knowhow.shield.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knowhow.shield.Exception.VerificationTokeIsNotValidException;
import com.knowhow.shield.model.User;
import com.knowhow.shield.model.VerificationToken;
import com.knowhow.shield.repository.VerificationTokenRepository;
import com.knowhow.shield.service.VerificationTokenService;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceImplTest {

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private Clock clock;

    private Clock fixedClock;

    private VerificationTokenService verificationTokenService;

    private User user;

    private VerificationToken verificationToken;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final static LocalDate LOCAL_DATE = LocalDate.of(2000, 01, 01);

    @Before
    public void init() {
        verificationTokenService = new VerificationTokenServiceImpl(clock, verificationTokenRepository);
        fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        user = mock(User.class);
        verificationToken = mock(VerificationToken.class);
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    @Test
    public void givenUserExpectCreateVerificationTokenValidFor24H() {
        //Arrange
        VerificationToken verificationToken = mock(VerificationToken.class);
        when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);
        ArgumentCaptor<VerificationToken> tokenCaptor = ArgumentCaptor.forClass(VerificationToken.class);
        //Act
        verificationTokenService.createVerificationToken(user);

        //Assert
        verify(verificationTokenRepository).save(tokenCaptor.capture());
        assertThat(tokenCaptor.getValue().getExpiryDate()).isEqualTo(LocalDateTime.now(fixedClock).plusHours(24));
    }

    @Test
    public void givenSameUserExpectDifferentToken() {
        //Arrange
        User user = mock(User.class);
        when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);

        //Act
        String token1 = verificationTokenService.createVerificationToken(user);
        String token2 = verificationTokenService.createVerificationToken(user);

        //Assert
        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void givenValidTokenToFindUserExpectValidUser() {
        //Arrange
        when(verificationToken.getExpiryDate()).thenReturn(LocalDateTime.now(fixedClock).plusHours(1));
        when(verificationToken.getUser()).thenReturn(user);
        when(verificationTokenRepository.findByTokenAndActiveTrue("1234")).thenReturn(Optional.of(verificationToken));

        //Act
        User result = verificationTokenService.findUserByToken("1234");

        //Assert
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void givenInvalidTokenToFindUserExpectException() {
        //Arrange
        when(verificationTokenRepository.findByTokenAndActiveTrue("1234")).thenReturn(Optional.empty());
        exception.expect(VerificationTokeIsNotValidException.class);
        exception.expectMessage("this token: 1234 is invalid");

        //Act
        verificationTokenService.findUserByToken("1234");

        //Assert is done by Rule
    }

    @Test
    public void givenExpiredTokenToFindUserExpectException() {
        //Arrange
        when(verificationTokenRepository.findByTokenAndActiveTrue("1234")).thenReturn(Optional.of(verificationToken));
        when(verificationToken.getExpiryDate()).thenReturn(LocalDateTime.now(fixedClock).minusHours(25));
        exception.expect(VerificationTokeIsNotValidException.class);
        exception.expectMessage("this token: 1234 is expired");

        //Act
        verificationTokenService.findUserByToken("1234");

        //Assert is done by Rule
    }

    @Test
    public void givenValidTokenToDeactivateExpectDeactivated() {
        //Arrange
        when(verificationTokenRepository.findByTokenAndActiveTrue("1234")).thenReturn(Optional.of(verificationToken));
        when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);
        ArgumentCaptor<VerificationToken> tokenCaptor = ArgumentCaptor.forClass(VerificationToken.class);

        //Act
        verificationTokenService.deactivateToken("1234");

        //Assert
        verify(verificationTokenRepository).save(tokenCaptor.capture());
        assertThat(tokenCaptor.getValue().isActive()).isFalse();
    }

    @Test
    public void givenInvalidTokenToDeactivateExpectException() {
        //Arrange
        when(verificationTokenRepository.findByTokenAndActiveTrue("1234")).thenReturn(Optional.empty());
        exception.expect(VerificationTokeIsNotValidException.class);
        exception.expectMessage("this token: 1234 is invalid");

        //Act
        verificationTokenService.deactivateToken("1234");

        //Assert is done by Rule
    }
}
