package com.duplex.house.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duplex.house.model.Logement;
import com.duplex.house.model.Reservation;
import com.duplex.house.model.ReservationId;
import com.duplex.house.model.User;
import com.duplex.house.repository.LogementRepository;
import com.duplex.house.repository.ReservationRepository;
import com.duplex.house.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservationService {
	
	@Autowired
    private ReservationRepository reservationRepository;
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private LogementRepository logementRepository;
    
    @Autowired
    private RandomStringGenerator generator;
    
    public Reservation createReservation(Reservation reservation) {
    	   	
    	// 1. Récupérer les entités réelles pour la relation Many-to-One
    	User utilisateur = userRepository.findById(reservation.getUserId().getId())
                .orElseThrow( () -> new EntityNotFoundException("Utilisateur non trouvé"));

    	Logement logement = logementRepository.findById(reservation.getLogementId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Logement non trouvé"));
        
     //Génération d'un code de resrvation aléatoirement de 8 caractères
        String randomCode = generator.generateRandomString(8);
        
     // 2. Créer l'objet ReservationId
        ReservationId reservationId = new ReservationId();
        reservationId.setUserId(utilisateur.getId());
        reservationId.setLogementId(logement.getId());
        reservationId.setCodeReservation(randomCode); // Assigner un code de reservation aléatoire
        
     // 3. Assigner les entités et l'ID à l'objet Reservation
        reservation.setId(reservationId);
        reservation.setUserId(utilisateur);
        reservation.setLogementId(logement);
        reservation.setDateReservation(new Date());
        reservation.setTotalPrice(reservation.getTotalPrice());
    	
    	// 4. Sauvegarder et retourner l'entité
        return reservationRepository.save(reservation);
    	
    }

}
