package com.knowhow.shield.event;

import com.knowhow.shield.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class UserEvent {

    private final BCryptPasswordEncoder passwordEncoder;

    @HandleBeforeCreate
    public void handleUserCreate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @HandleBeforeSave
    public void handleUserUpdate(User user) {
        if (user.getPassword() != null && !"".equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }
}
