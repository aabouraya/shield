package com.knowhow.shield.listener;

import com.knowhow.shield.event.CompleteRegistrationEvent;
import com.knowhow.shield.service.ActivationService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CompleteRegistrationListener implements ApplicationListener<CompleteRegistrationEvent> {

    private ActivationService activationService;

    CompleteRegistrationListener(ActivationService activationService) {

        this.activationService = activationService;
    }


    // get User from Event
    // generate UUID
    // create verification token
    // send email message
    @Override
    public void onApplicationEvent(CompleteRegistrationEvent event) {
        activationService.sendActivationEmail(event.getUser());

    }
}
