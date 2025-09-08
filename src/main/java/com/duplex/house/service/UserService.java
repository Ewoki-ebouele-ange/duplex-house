package com.duplex.house.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duplex.house.model.User;
import com.duplex.house.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	
	public Optional<User> softDelete(Long id) {
		
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
        	User user = optionalUser.get();
            user.setDeletedAt(new Date()); // Définit la date de suppression logique
            userRepository.save(user); // Sauvegarde l'entité mise à jour
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
	
}
