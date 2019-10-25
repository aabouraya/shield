package com.knowhow.shield.controller;

import com.knowhow.shield.dto.OAuthClientDto;
import com.knowhow.shield.service.OAuthClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthClientController {

    private final OAuthClientService oauthClientService;

    @GetMapping("/oauth-clients/{id}")
    public OAuthClientDto get(@PathVariable String id) {
        return oauthClientService.get(id);
    }

    @GetMapping("/oauth-clients")
    public List<OAuthClientDto> getAll() {
        return oauthClientService.getAll();
    }

    @PostMapping("/oauth-clients")
    public void create(OAuthClientDto client) {
        oauthClientService.create(client);
    }

}
