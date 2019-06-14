package com.knowhow.shield.service.impl;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.event.CompleteRegistrationEvent;
import com.knowhow.shield.model.User;
import com.knowhow.shield.service.RegistrationService;
import com.knowhow.shield.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
class RegistrationServiceImpl implements RegistrationService {

    private UserService userService;
    private ApplicationEventPublisher publisher;

    RegistrationServiceImpl(UserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @Override
    public Long register(RegistrationDto registrationDto) {
        User user = userService.createUser(registrationDto);
        publisher.publishEvent(new CompleteRegistrationEvent(user));
        return user.getId();
    }
}
