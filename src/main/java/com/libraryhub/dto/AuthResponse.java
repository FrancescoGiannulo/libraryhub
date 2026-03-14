package com.libraryhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token; //Il sistema risponde alla richiesta di autenticazione con il token
}
