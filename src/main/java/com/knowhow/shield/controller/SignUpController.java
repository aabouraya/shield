package com.knowhow.shield.controller;

import com.knowhow.shield.dto.RegistrationDto;
import com.knowhow.shield.service.ActivationService;
import com.knowhow.shield.service.RegistrationService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    private RegistrationService registrationService;
    private ActivationService activationService;

    SignUpController(RegistrationService registrationService, ActivationService activationService) {
        this.activationService = activationService;
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public Long signUp(HttpServletRequest request, @Valid RegistrationDto user) {
        request.getRequestURI();
        return registrationService.register(user);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "is live";
    }

    @RequestMapping(value = "/activation/{token}", method = RequestMethod.POST)
    public void activate(@PathVariable String token) {
        activationService.activateUser(token);
    }
}
