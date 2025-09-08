package com.duplex.house.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duplex.house.model.User;
import com.duplex.house.repository.UserRepository;
import com.duplex.house.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private UserService userService; // Assurez-vous que cette ligne est présente


	// API pour consulter la liste des utilisateurs
    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // API pour ajouter un utilisateur
    @PostMapping("/add-users")
    public User addUser(@RequestBody User utilisateur) {
        return userRepository.save(utilisateur);
    }
    
    // API pour consulter un utilisateur
    @GetMapping("/{id}/edit")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
    	Optional<User> utilisateur = userRepository.findById(id);
        
        if (utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // API pour modifier un utilisateur
    @PutMapping("/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setNom(userDetails.getNom());
            existingUser.setPrenom(userDetails.getPrenom());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setVille(userDetails.getVille());
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            existingUser.setImgProfilPath(userDetails.getImgProfilPath());
            existingUser.setPseudo(userDetails.getPseudo());
            existingUser.setBio(userDetails.getBio());
            

            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> deletedUser = userService.softDelete(id);

        if (deletedUser.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
