package com.knowhow.shield.service.impl;

import com.knowhow.shield.Exception.VerificationTokeIsNotValidException;
import com.knowhow.shield.model.User;
import com.knowhow.shield.model.VerificationToken;
import com.knowhow.shield.repository.VerificationTokenRepository;
import com.knowhow.shield.service.VerificationTokenService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;

    VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public String createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user, UUID.randomUUID().toString(),
                LocalDateTime.now().plusHours(24), true);
        verificationTokenRepository.save(verificationToken);
        return verificationToken.getToken();
    }

    @Override
    public User findUserByToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByTokenAndActiveTrue(token).orElseThrow(
                () -> new VerificationTokeIsNotValidException(String.format("this token: %s is invalid", token)));

        if (LocalDateTime.now().isAfter(verificationToken.getExpiryDate())) {
            throw new VerificationTokeIsNotValidException(String.format("this token: %s is expired", token));
        }
        return verificationToken.getUser();
    }

    @Override
    public void deactivateToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByTokenAndActiveTrue(token).orElseThrow(
                () -> new VerificationTokeIsNotValidException(String.format("this token: %s is invalid", token)));
        verificationToken.setActive(false);
        verificationTokenRepository.save(verificationToken);
    }


}
