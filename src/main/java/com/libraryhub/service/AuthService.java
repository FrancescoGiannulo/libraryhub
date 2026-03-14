package com.libraryhub.service;

import com.libraryhub.dto.AuthResponse;
import com.libraryhub.dto.LoginRequest;
import com.libraryhub.dto.RegisterRequest;
import com.libraryhub.entity.User;
import com.libraryhub.repository.UserRepository;
import com.libraryhub.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        // 1. Verifica che email e username non siano già in uso
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already in use");
        }

        // 2. Costruisce l'entità User con la password hashata
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // 3. Salva l'utente nel DB
        userRepository.save(user);

        // 4. Genera e restituisce il token
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        // 1. Verifica email e password — lancia eccezione automaticamente se errate
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Carica l'utente dal DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Genera e restituisce il token
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
