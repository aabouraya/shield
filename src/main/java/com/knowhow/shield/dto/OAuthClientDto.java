package com.knowhow.shield.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthClientDto {

    private String client;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoApprove;
}
