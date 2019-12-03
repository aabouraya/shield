package com.knowhow.shield.service.impl;

import com.google.common.collect.ImmutableMap;
import com.knowhow.shield.mapping.UserMapper;
import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
@RequiredArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByNaturalId(ImmutableMap.of("email", username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return userMapper.toUserDetails(user);

    }
}

