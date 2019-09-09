package com.knowhow.shield.service;

import com.knowhow.shield.dto.RegistrationDto;
import java.util.UUID;

public interface RegistrationService {

    UUID register(RegistrationDto registrationDto);

}
