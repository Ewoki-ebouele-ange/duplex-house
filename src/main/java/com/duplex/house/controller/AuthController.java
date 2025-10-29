package com.duplex.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.duplex.house.dto.AuthResponse;
import com.duplex.house.model.User;
import com.duplex.house.repository.UserRepository;
import com.duplex.house.security.JwtGenerator;

@RestController
@RequestMapping("/user/auth")
public class AuthController {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager; // Nécessite une configuration supplémentaire, voir ci-dessous
    
    @Autowired
    private JwtGenerator jwtGenerator; // Injection du générateur de token
	
    // ----------------------------------------------------------------------
    // 1. Inscription (Sign-Up)
    // ----------------------------------------------------------------------
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        
        // Vérification de l'existence de l'utilisateur
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>("Cet email est déjà pris!", HttpStatus.BAD_REQUEST);
        } else {
        	// Hachage du mot de passe
            user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));

            // Sauvegarde de l'utilisateur dans la base de données
            userRepository.save(user);

            return new ResponseEntity<>("Utilisateur enregistré avec succès!", HttpStatus.CREATED);
        }

        
    }
    
 // ----------------------------------------------------------------------
    // 2. Connexion (Sign-In)
    // ----------------------------------------------------------------------
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody User loginRequest) {
        
        // Tentative d'authentification
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMotDePasse()
                )
        );

        // Si l'authentification réussit, l'information est stockée
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Génération du JWT**
        String token = jwtGenerator.generateToken(authentication);

        // En l'absence de JWT, on retourne juste un message de succès
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    
}
