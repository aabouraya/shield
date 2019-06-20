package com.knowhow.shield.service.impl;

import com.knowhow.shield.Exception.UserIsAlreadyExistException;
import com.knowhow.shield.Exception.UserNotFoundException;
import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import com.knowhow.shield.service.UserService;
import java.util.stream.Collectors;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public User createUser(RegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserIsAlreadyExistException(registrationDto.getEmail());
        }
//        modelMapper.addConverter(getUserConvertor(), RegistrationDto.class, User.class);
//        User user = modelMapper.map(registrationDto, User.class);

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    private Converter getUserConvertor() {
        Converter<RegistrationDto, User> myConverter = new Converter<RegistrationDto, User>() {
            public User convert(MappingContext<RegistrationDto, User> context) {
                RegistrationDto s = context.getSource();
                User d = context.getDestination();
                d.setFirstName(s.getFirstName());
                d.setLastName(s.getLastName());
                d.setPassword(passwordEncoder.encode(s.getPassword()));
                d.setEnabled(false);
                return d;
            }
        };
        return myConverter;
    }
}
