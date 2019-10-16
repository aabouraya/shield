package com.knowhow.shield.service.impl;

import com.knowhow.shield.dto.OAuthClientDto;
import com.knowhow.shield.mapping.OAuthClientMapper;
import com.knowhow.shield.model.OAuthClient;
import com.knowhow.shield.repository.OauthClientDetailsRepository;
import com.knowhow.shield.service.OAuthClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthClientServiceImpl implements OAuthClientService {

    private final OauthClientDetailsRepository oauthClientDetailsRepository;
    private final OAuthClientMapper mapper;

    @Override
    public OAuthClientDto get(String id) {
        OAuthClient client = oauthClientDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException(id));
        return mapper.toDto(client);
    }

    @Override
    public List<OAuthClientDto> getAll() {
        List<OAuthClient> modelList = oauthClientDetailsRepository.findAll();
        return mapper.toDtoList(modelList);
    }

    @Override
    public void create(OAuthClientDto client) {
        oauthClientDetailsRepository.save(mapper.toModel(client));
    }
}
