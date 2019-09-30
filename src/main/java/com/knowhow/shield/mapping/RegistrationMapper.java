package com.knowhow.shield.mapping;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.model.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class RegistrationMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User toUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEnabled(false);
        return user;
    }
}
