package com.knowhow.shield.mapping;

import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    default UserDetails toUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true, null);
    }

    void updateFromDto(UserDto userDto, @MappingTarget User user);

}
