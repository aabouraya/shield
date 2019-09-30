package com.knowhow.shield.service.impl;

import com.knowhow.shield.Exception.UserIsAlreadyExistException;
import com.knowhow.shield.Exception.UserNotFoundException;
import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.mapping.RegistrationMapper;
import com.knowhow.shield.mapping.UserMapper;
import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import com.knowhow.shield.service.UserService;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RegistrationMapper registrationMapper;

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        userRepository.delete(user);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        return new PageImpl<>(
                usersPage.getContent().stream().map(user -> userMapper.toDto(user)).collect(Collectors.toList()),
                pageable, usersPage.getTotalElements());
    }

    @Override
    public void updateUser(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        userMapper.updateFromDto(userDto, user);
        userRepository.save(user);
    }

    @Override
    public User createUser(RegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserIsAlreadyExistException(registrationDto.getEmail());
        }
        User user = registrationMapper.toUser(registrationDto);
        return userRepository.save(user);
    }
}
