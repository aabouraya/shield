package com.knowhow.shield.controller;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.service.ActivationService;
import com.knowhow.shield.service.RegistrationService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
class RegistrationController {

    private final RegistrationService registrationService;
    private final ActivationService activationService;

    RegistrationController(RegistrationService registrationService, ActivationService activationService) {
        this.activationService = activationService;
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/users/register")
    public ResponseEntity<Long> signUp(@Valid @RequestBody RegistrationDto user) {
        return new ResponseEntity(registrationService.register(user), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/activate/{token}")
    public ResponseEntity<UUID> activate(@PathVariable String token) {
        return new ResponseEntity(activationService.activateUser(token), HttpStatus.OK);
    }
}
