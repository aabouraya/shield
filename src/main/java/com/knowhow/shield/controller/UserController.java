package com.knowhow.shield.controller;

import com.knowhow.shield.dto.UserDto;
import com.knowhow.shield.service.UserService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/shield", produces = MediaType.APPLICATION_JSON_VALUE)
class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN_READ')")
    public Page<UserDto> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @PutMapping("/users/{id}")
    @Secured("ROLE_ADMIN_UPDATE")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable UUID id, @Valid @RequestBody UserDto user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @Secured("ROLE_ADMIN_DELETE")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
