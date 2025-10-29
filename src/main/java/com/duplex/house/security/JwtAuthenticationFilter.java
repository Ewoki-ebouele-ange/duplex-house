package com.duplex.house.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator jwtGenerator; // Pour valider le jeton et extraire l'email

    @Autowired
    private UserDetailsService userDetailsService; // Pour charger l'utilisateur et ses rôles

    /**
     * Cette méthode est le cœur du filtre. Elle est exécutée pour chaque requête.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Extraire le JWT de la requête HTTP
        String token = getJwtFromRequest(request);

        // 2. Vérifier si le jeton est valide et que l'utilisateur n'est pas déjà authentifié
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
            
            // 3. Récupérer l'identifiant (email) de l'utilisateur à partir du jeton
            String username = jwtGenerator.getUsernameFromToken(token);

            // 4. Charger l'utilisateur AVEC SES RÔLES (via UserDetailsService)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5. Créer un objet d'authentification pour Spring Security
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Le mot de passe n'est pas nécessaire ici (authentification basée sur le token)
                    userDetails.getAuthorities() // ⬅️ Les rôles (Authorities) sont inclus
            );
            
            // 6. Définir les détails de l'authentification (comme l'adresse IP de la requête)
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 7. Placer l'objet Authentication dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 8. Poursuivre la chaîne de filtres (permettre à la requête d'atteindre le contrôleur)
        filterChain.doFilter(request, response);
    }

    /**
     * Méthode utilitaire pour extraire le JWT de l'en-tête "Authorization".
     * Le format attendu est "Bearer <jeton>".
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // Vérifie si l'en-tête est présent et commence par "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Retourne le jeton sans le préfixe "Bearer " (qui fait 7 caractères)
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}