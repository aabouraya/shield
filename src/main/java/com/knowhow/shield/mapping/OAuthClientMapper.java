package com.knowhow.shield.mapping;

import com.knowhow.shield.dto.OAuthClientDto;
import com.knowhow.shield.model.OAuthClient;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class OAuthClientMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract OAuthClientDto toDto(OAuthClient model);

    public abstract List<OAuthClientDto> toDtoList(List<OAuthClient> modelList);

    @Mapping(target = "clientSecret", ignore = true)
    protected abstract OAuthClient toModelExcludeSecret(OAuthClientDto registrationDto);

    public OAuthClient toModel(OAuthClientDto dto) {
        OAuthClient model = toModelExcludeSecret(dto);
        model.setClientSecret(passwordEncoder.encode(dto.getClientSecret()));
        return model;
    }

}
