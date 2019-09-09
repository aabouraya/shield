package com.knowhow.shield.service.impl;

import com.knowhow.shield.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetailsService;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;


    UserDetailsService userDetailsService;


    @Before
    public void init() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void notValidYet() {
        System.out.println("not valid yet");
    }
}
