package com.knowhow.shield.service.impl;

import com.knowhow.shield.Exception.UserNotFoundException;
import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import com.knowhow.shield.service.UserService;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        userRepository.delete(user);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return new PageImpl<>(usersPage.getContent().stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList()), pageable, usersPage.getTotalElements());
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        modelMapper.map(userDto, user);
        userRepository.save(user);
    }
}
