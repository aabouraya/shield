package com.knowhow.shield.controller;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.service.ActivationService;
import com.knowhow.shield.service.RegistrationService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;
    private ActivationService activationService;

    RegistrationController(RegistrationService registrationService, ActivationService activationService) {
        this.activationService = activationService;
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Long> signUp(@Valid @RequestBody RegistrationDto user) {
        return new ResponseEntity(registrationService.register(user), HttpStatus.CREATED);
    }

    @GetMapping(value = "/ping")
    public String ping() {
        return "is live";
    }

    @PostMapping(value = "/activation/{token}")
    public void activate(@PathVariable String token) {
        activationService.activateUser(token);
    }
}
