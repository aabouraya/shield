package com.knowhow.shield.service;

import com.knowhow.shield.model.User;

public interface EmailService {

    void sendActivationEmail(User user, String token);
}
