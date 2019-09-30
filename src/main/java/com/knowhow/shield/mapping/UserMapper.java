package com.knowhow.shield.mapping;

import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    void updateFromDto(UserDto userDto, @MappingTarget User user);

}
