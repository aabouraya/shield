package com.knowhow.shield.service.impl;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.event.CompleteRegistrationEvent;
import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import com.knowhow.shield.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
class RegistrationServiceImpl implements RegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ApplicationEventPublisher publisher;
    private ModelMapper modelMapper;

    RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            ApplicationEventPublisher publisher, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long register(RegistrationDto registrationDto) {
        userRepository.findByEmail(registrationDto.getEmail());
//        modelMapper.createTypeMap(RegistrationDto.class, User.class)
//                .addMapping(RegistrationDto::getPassword,User::setPassword);
//        User user = modelMapper.map(registrationDto, User.class);
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
        publisher.publishEvent(new CompleteRegistrationEvent(user));
        return user.getId();
    }
}
