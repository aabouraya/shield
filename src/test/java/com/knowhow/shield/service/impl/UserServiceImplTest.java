package com.knowhow.shield.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.knowhow.shield.Exception.UserIsAlreadyExistException;
import com.knowhow.shield.Exception.UserNotFoundException;
import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.model.User;
import com.knowhow.shield.repository.UserRepository;
import com.knowhow.shield.service.UserService;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(userRepository, modelMapper, passwordEncoder);
    }

    @Test
    public void givenValidUserExpectDeleteUser() {
        //Arrange
        User user = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        //Act
        userService.deleteUser(1L);

        //Assert
        verify(userRepository).delete(user);
    }

    @Test
    public void givenInvalidUserExpectException() {
        //Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        exception.expect(UserNotFoundException.class);
        exception.expectMessage("This User: 1 is not exist");

        //Act
        userService.deleteUser(1L);

        //Assert is done by Rule
    }

    @Test
    public void givenValidUserIdExpectUpdateUser() {
        //Arrange
        UserDto dto = mock(UserDto.class);
        User user = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(modelMapper).map(any(UserDto.class), any(User.class));

        //Act
        userService.updateUser(1L, dto);

        //Assert
        verify(modelMapper).map(dto, user);
        verify(userRepository).save(user);
    }

    @Test
    public void givenInvalidUserForUpdateExpectException() {
        //Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        UserDto dto = mock(UserDto.class);
        exception.expect(UserNotFoundException.class);
        exception.expectMessage("This User: 1 is not exist");

        //Act
        userService.updateUser(1L, dto);

        //Assert is done by Rule
    }

    @Test
    public void givenValidRegistrationExpectSaveUser() {
        //Arrange
        RegistrationDto dto = mock(RegistrationDto.class);
        User user = mock(User.class);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("abc123");

        //Act
        User result = userService.createUser(dto);

        //Assert
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void givenExistUserExpectException() {
        //Arrange
        RegistrationDto dto = new RegistrationDto();
        User user = mock(User.class);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        exception.expect(UserIsAlreadyExistException.class);

        //Act
        userService.createUser(dto);

        //Assert is done by Rule
    }

    @Test
    public void givenValidPageableExpectAllUsers() {
        //Arrange
        Pageable pageable = mock(Pageable.class);
        User user = mock(User.class);
        UserDto dto = mock(UserDto.class);
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl(Arrays.asList(user)));
        when(modelMapper.map(user, UserDto.class)).thenReturn(dto);

        //Act
        Page<UserDto> result = userService.getUsers(pageable);

        //Assert
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }
}
