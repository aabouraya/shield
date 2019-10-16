package com.knowhow.shield.mapping;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class RegistrationMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", ignore = true)
    protected abstract User toUserExcludePassword(RegistrationDto registrationDto);

    public User toUser(RegistrationDto registrationDto) {
        User user = toUserExcludePassword(registrationDto);
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        return user;
    }
}
