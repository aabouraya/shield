package com.knowhow.shield.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class RegistrationServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    private RegistrationServiceImpl registrationServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
       // userServiceImplUnderTest = new UserServiceImpl(mockUserRepository);
    }

    @Test
    public void testCreateUser() {
        // Setup
        final RegistrationDto registrationDto = null;
        final Long expectedResult = 0L;
        when(mockUserRepository.findByEmail("email")).thenReturn(null);
        when(mockUserRepository.save(null)).thenReturn(null);

        // Run the test
     //   final Long result = userServiceImplUnderTest.register(userDto);

        // Verify the results
      //  assertEquals(expectedResult, result);
    }
}
