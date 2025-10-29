package com.duplex.house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.duplex.house.model.User;
import com.duplex.house.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
    private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // La méthode attend le nom d'utilisateur, nous utilisons l'email
        User user = userRepository.findByEmail(email) 
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Retourne un objet UserDetails de Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getMotDePasse(), // Assurez-vous que c'est le mot de passe HACHÉ
                user.getRoles()
                //Collections.emptyList() // Pour l'instant, pas de rôles/autorités
        );
    }
	
}
