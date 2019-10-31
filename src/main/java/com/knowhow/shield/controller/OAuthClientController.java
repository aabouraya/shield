package com.knowhow.shield.controller;

import com.knowhow.shield.dto.OAuthClientDto;
import com.knowhow.shield.service.OAuthClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthClientController {

    private final OAuthClientService oauthClientService;

    @GetMapping("/oauth-clients/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN_READ')")
    public OAuthClientDto get(@PathVariable String id) {
        return oauthClientService.get(id);
    }

    @GetMapping("/oauth-clients")
    @PreAuthorize("hasRole('ROLE_ADMIN_READ')")
    public List<OAuthClientDto> getAll() {
        return oauthClientService.getAll();
    }

    @PostMapping("/oauth-clients")
    @PreAuthorize("hasRole('ROLE_ADMIN_CREATE')")
    public void create(OAuthClientDto client) {
        oauthClientService.create(client);
    }

    @PutMapping("/oauth-clients")
    @PreAuthorize("hasRole('ROLE_ADMIN_UPDATE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(OAuthClientDto client) {

    }

    @DeleteMapping("/oauth-clients/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {

    }
}
