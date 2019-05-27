package com.knowhow.shield.dto;

import com.knowhow.shield.validation.PasswordMatches;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@PasswordMatches
public class RegistrationDto {

    @NotNull
    @NotEmpty
    private String firstName;

    private String lastName;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @Email
    private String email;

}
