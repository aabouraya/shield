package com.knowhow.shield.service;

import com.knowhow.shield.model.User;
import java.util.UUID;

public interface ActivationService {

    void sendActivationEmail(User user);

    UUID activateUser(String token);
}
