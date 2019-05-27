package com.knowhow.shield.service;

import com.knowhow.shield.model.User;

public interface VerificationTokenService {

    String createVerificationToken(User user);

    User findUserByToken(String token);

    void deactivateToken(String token);
}
