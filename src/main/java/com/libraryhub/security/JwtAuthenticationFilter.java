package com.libraryhub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException{
        //1. leggere l'header authorization
        final String authHeader = request.getHeader("Authorization");

        //Se manca o non inizia con bearer: lascia passare senza autenticare
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //estrarre il token rimuovendo bearer
        final String jwt=authHeader.substring(7);

        //estrarre l'username
        final String userEmail=jwtService.extractUsername(jwt);

        //se lo username è valido e l'utente non è già autenticato
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null){
            //carica l'utente dal DB
            UserDetails userDetails= userDetailsService.loadUserByUsername(userEmail);

            // valida il token
            if(jwtService.isTokenValid(jwt, userDetails)){
                //crea il token di autenticazione
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //aggiunge i dettagli della request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 10. Imposta l'utente nel SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        // 11. Passa al filtro successivo nella catena
        filterChain.doFilter(request, response);
        }
    }
}
