package com.knowhow.shield.service;

import com.knowhow.shield.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void deleteUser(Long id);

    Page<UserDto> getUsers(Pageable pageable);

    void updateUser(Long id, UserDto user);
}
