/**
 * 
 */
package com.duplex.house.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.duplex.house.model.Piece;
import com.duplex.house.model.User;
import com.duplex.house.repository.PieceRepository;
import com.duplex.house.repository.UserRepository;

/**
 * 
 */
@Service
public class PieceService {

	@Autowired
    private PieceRepository pieceRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public Optional<Piece> softDelete(Integer id) {
		
		// 1. Récupérer l'Email de l'utilisateur connecté (via Spring Security)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String connectedUserEmail = authentication.getName(); // L'email est généralement le 'principal' ou le nom d'utilisateur

        // 2. Trouver l'entité Utilisateur connectée (pour obtenir son ID)
        Optional<User> optionalConnectedUser = userRepository.findByEmail(connectedUserEmail);
        
        if (optionalConnectedUser.isEmpty()) {
            // Devrait rarement arriver si l'utilisateur est bien authentifié
            throw new IllegalStateException("Impossible de trouver l'utilisateur connecté dans la base de données.");
        }
        
        Integer connectedUserId = optionalConnectedUser.get().getId();
		
        Optional<Piece> optionalPiece = pieceRepository.findById(id);

        if (optionalPiece.isPresent()) {
        	Piece piece = optionalPiece.get();
        	piece.setDeletedAt(new Date()); // Définit la date de suppression logique
        	piece.setDeletedBy(connectedUserId); // **<-- Le champ mis à jour**
        	
        	pieceRepository.save(piece); // Sauvegarde l'entité mise à jour
            return Optional.of(piece);
        } else {
            return Optional.empty();
        }
    }
	
}
