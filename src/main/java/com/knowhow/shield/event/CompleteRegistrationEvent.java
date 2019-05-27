package com.knowhow.shield.event;

import com.knowhow.shield.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CompleteRegistrationEvent extends ApplicationEvent {

    private User user;
    public CompleteRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }
}
