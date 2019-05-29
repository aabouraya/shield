package com.knowhow.shield.controller;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.service.ActivationService;
import com.knowhow.shield.service.RegistrationService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    private RegistrationService registrationService;
    private ActivationService activationService;

    SignUpController(RegistrationService registrationService, ActivationService activationService) {
        this.activationService = activationService;
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/registration")
    public Long signUp(HttpServletRequest request, @Valid RegistrationDto user) {
        request.getRequestURI();
        return registrationService.register(user);
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
