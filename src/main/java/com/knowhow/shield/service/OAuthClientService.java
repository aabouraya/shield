package com.knowhow.shield.service;

import com.knowhow.shield.dto.OAuthClientDto;
import java.util.List;

public interface OAuthClientService {

    OAuthClientDto get(String id);

    List<OAuthClientDto> getAll();

    void create(OAuthClientDto client);
}
