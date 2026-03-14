package com.libraryhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email is required to log in")
    @Email(message = "Must give a valid email")
    private String email;

    @NotBlank(message = "Password is required to log in")
    private String password;
}
