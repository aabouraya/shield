package com.knowhow.shield.mapping;

import static java.util.stream.Collectors.toSet;

import com.knowhow.shield.dto.EnhancedUserDetails;
import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.model.Role;
import com.knowhow.shield.model.User;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    default UserDetails toUserDetails(User user) {
        Set<SimpleGrantedAuthority> grantedAuthority = new HashSet<>();
        for (Role r : user.getRoles()) {
            grantedAuthority.addAll(r.getPrivileges().stream()
                    .map(p -> new SimpleGrantedAuthority("ROLE_" + r.getName() + "_" + p)).collect(toSet()));
        }

        EnhancedUserDetails enhancedUserDetails = new EnhancedUserDetails(user.getEmail(), user.getPassword(),
                user.isEnabled(), !user.isAccountExpired(), !user.isCredentialsExpired(), !user.isAccountLocked(),
                grantedAuthority);
        enhancedUserDetails.setPartyId(user.getPartyId());
        return enhancedUserDetails;
    }

    void updateFromDto(UserDto userDto, @MappingTarget User user);
}
