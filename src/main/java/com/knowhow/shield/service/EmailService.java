package com.knowhow.shield.service;

import com.knowhow.postoffice.contract.EmailDto;
import com.knowhow.shield.model.User;

public interface EmailService {

    EmailDto sendActivationEmail(User user, String token);

}
