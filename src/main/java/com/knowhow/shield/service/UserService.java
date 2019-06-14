package com.knowhow.shield.service;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void deleteUser(Long id);

    Page<UserDto> getUsers(Pageable pageable);

    void updateUser(Long id, UserDto user);

    User createUser(RegistrationDto userDto);
}
