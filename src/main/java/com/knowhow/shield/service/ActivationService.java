package com.knowhow.shield.service;

import com.knowhow.shield.model.User;

public interface ActivationService {

    void sendActivationEmail(User user);

    void activateUser(String token);
}
