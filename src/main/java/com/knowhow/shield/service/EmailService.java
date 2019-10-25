package com.knowhow.shield.service;

import com.knowhow.shield.dto.EmailDto;
import com.knowhow.shield.model.User;

public interface EmailService {

    EmailDto sendActivationEmail(User user, String token);

    // void send(ActivationMessageDto message);
}
