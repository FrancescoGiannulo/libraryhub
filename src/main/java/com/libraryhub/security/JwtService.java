package com.libraryhub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}") //Spring fa il retrieve della chiave nel file yml e lo inietta in secretKey
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    // Genera un token a partire dall'utente
    public String generateToken(UserDetails userDetails) {  //userdetails è l'interfaccia di spring security che rappresenta un utente autenticato
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))  //Esattamente 24 ore dalla creazione
                .signWith(getSigningKey())
                .compact(); //compatta in formato header.payload.signature
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); //estrae l'username dal token e lo confronta con quello di userdetails
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        //metodo generico che estrae sia stringhe che date
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);    //decodifica la chiave in una string di byte grezzi
        return Keys.hmacShaKeyFor(keyBytes);    //trasforma i byte grezzi in un oggetto secret key
        //utilizzabile dall'algoritmo HMAC-SHA256. jjwt sceglie automaticamente SHA-256, SHA-384 o SHA-512 in base alla lunghezza della chiave
    }
}
