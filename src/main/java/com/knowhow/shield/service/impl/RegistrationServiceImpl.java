package com.knowhow.shield.service.impl;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.event.CompleteRegistrationEvent;
import com.knowhow.shield.model.User;
import com.knowhow.shield.service.RegistrationService;
import com.knowhow.shield.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @Override
    public UUID register(RegistrationDto registrationDto) {
        User user = userService.createUser(registrationDto);
        publisher.publishEvent(new CompleteRegistrationEvent(user));
        return user.getId();
    }
}
