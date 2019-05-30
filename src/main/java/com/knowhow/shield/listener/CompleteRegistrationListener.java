package com.knowhow.shield.listener;

import com.knowhow.shield.event.CompleteRegistrationEvent;
import com.knowhow.shield.service.ActivationService;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CompleteRegistrationListener implements ApplicationListener<CompleteRegistrationEvent> {

    private ActivationService activationService;

    CompleteRegistrationListener(ActivationService activationService) {

        this.activationService = activationService;
    }

    @Override
    //@Async
    public void onApplicationEvent(CompleteRegistrationEvent event) {
        activationService.sendActivationEmail(event.getUser());

    }
}
