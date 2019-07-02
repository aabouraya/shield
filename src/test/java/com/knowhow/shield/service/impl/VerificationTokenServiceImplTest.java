package com.knowhow.shield.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knowhow.shield.model.User;
import com.knowhow.shield.model.VerificationToken;
import com.knowhow.shield.repository.VerificationTokenRepository;
import com.knowhow.shield.service.VerificationTokenService;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.Before;
import org.junit.Test;
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

    private final static LocalDate LOCAL_DATE = LocalDate.of(2000, 01, 01);

    @Before
    public void init() {
        verificationTokenService = new VerificationTokenServiceImpl(clock, verificationTokenRepository);
        fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    @Test
    public void givenUserExpectCreateVerificationTokenValidFor24H() {
        //Arrange
        User user = mock(User.class);
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
    public void givenSameUserExpectDifferntToken() {
        //Arrange
        User user = mock(User.class);
        VerificationToken verificationToken = mock(VerificationToken.class);
        when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);

        //Act
        String token1 = verificationTokenService.createVerificationToken(user);
        String token2 = verificationTokenService.createVerificationToken(user);

        //Assert
        assertThat(token1).isNotEqualTo(token2);
    }
}
