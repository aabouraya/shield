package com.knowhow.shield.service.impl;

import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import com.knowhow.shield.service.ActivationService;
import com.knowhow.shield.service.EmailService;
import com.knowhow.shield.service.VerificationTokenService;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
class ActivationServiceImpl implements ActivationService {

    private VerificationTokenService verificationTokenService;
    private EmailService emailService;
    private UserRepository userRepository;

    ActivationServiceImpl(VerificationTokenService verificationTokenService, EmailService emailService,
            UserRepository userRepository) {
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Override
    public void sendActivationEmail(User user) {
        String token = verificationTokenService.createVerificationToken(user);
        emailService.sendActivationEmail(user, token);
    }

    @Override
    @Transactional
    public Long activateUser(String token) {
        User user = verificationTokenService.findUserByToken(token);
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenService.deactivateToken(token);
        return user.getId();
    }

}
