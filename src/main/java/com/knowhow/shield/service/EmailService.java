package com.knowhow.shield.service;

import com.knowhow.shield.dto.ActivationMessageDto;
import com.knowhow.shield.model.User;

public interface EmailService {

    ActivationMessageDto sendActivationEmail(User user, String token);

    // void send(ActivationMessageDto message);
}
