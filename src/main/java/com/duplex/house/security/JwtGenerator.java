/**
 * 
 */
package com.duplex.house.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Slf4j // ⬅️ Cette annotation injecte la variable 'log'
@Component
public class JwtGenerator {
	
	@Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationTime;

    /**
     * Génère le JWT basé sur l'objet Authentication de Spring Security.
     * @param authentication L'objet d'authentification après validation du login.
     * @return Le jeton JWT sous forme de chaîne de caractères.
     */
    public String generateToken(Authentication authentication) {
        
        // Le nom d'utilisateur/email est récupéré ici
        String username = authentication.getName();
        
        // Date de création du jeton
        Date currentDate = new Date();
        
        // Date d'expiration (Création + Durée)
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationTime); 

        // Création de la clé de signature
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        String token = Jwts.builder()
                .subject(username) // Le corps du jeton (payload), ici l'email
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key) // Signature avec la clé secrète
                .compact();
        
        return token;
    }
    
    /**
     * Récupère l'email (Subject) de l'utilisateur à partir du jeton JWT.
     * @param token Le jeton JWT à décoder.
     * @return L'email de l'utilisateur.
     */
    public String getUsernameFromToken(String token) {
        
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        Claims claims = Jwts.parser()
                .verifyWith(key) // Vérifie la signature avec la clé secrète
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Le "Subject" (Sujet) est l'email que nous avons défini dans generateToken()
        return claims.getSubject(); 
    }

    /**
     * Valide la signature et l'expiration du jeton JWT.
     * @param token Le jeton JWT à valider.
     * @return true si le jeton est valide, false sinon.
     */
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            
            Jwts.parser()
                    .verifyWith(key) // Tente de vérifier la signature
                    .build()
                    .parseSignedClaims(token);
            
            return true; // Si aucune exception n'est lancée, le jeton est valide

        } catch (SignatureException e) {
            // Jeton mal signé
            log.error("Signature JWT non valide : {}", e.getMessage());
        } catch (MalformedJwtException e) {
            // Jeton mal formé
            log.error("Jeton JWT mal formé : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // Jeton expiré (c'est souvent la raison principale de l'échec)
            log.error("Le jeton JWT a expiré : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            // Jeton non supporté (mauvais format de JWS)
            log.error("Le jeton JWT n'est pas supporté : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // Chaîne de jeton vide
            log.error("La chaîne de revendications JWT est vide : {}", e.getMessage());
        }
        
        return false;
    }
	
}
