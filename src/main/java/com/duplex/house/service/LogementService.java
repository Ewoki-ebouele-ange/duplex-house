package com.duplex.house.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duplex.house.model.Logement;
import com.duplex.house.repository.LogementRepository;

import lombok.Data;

@Data
@Service
public class LogementService {
	
	@Autowired
    private LogementRepository logementRepository;
	
	public Optional<Logement> softDelete(Integer id) {
		
        Optional<Logement> optionalLogement = logementRepository.findById(id);

        if (optionalLogement.isPresent()) {
        	Logement logement = optionalLogement.get();
        	logement.setDeletedAt(new Date()); // Définit la date de suppression logique
            logementRepository.save(logement); // Sauvegarde l'entité mise à jour
            return Optional.of(logement);
        } else {
            return Optional.empty();
        }
    }

}
