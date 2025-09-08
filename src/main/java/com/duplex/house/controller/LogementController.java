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

import com.duplex.house.model.Logement;
import com.duplex.house.repository.LogementRepository;
import com.duplex.house.service.LogementService;

@RestController
@RequestMapping("/logements")
public class LogementController {

	@Autowired
    private LogementRepository logementRepository;
	
	@Autowired
    private LogementService logementService; // Assurez-vous que cette ligne est présente
	
	// API pour consulter la liste des utilisateurs
    @GetMapping("/")
    public List<Logement> getAllLogements() {
        return logementRepository.findAll();
    }
    
    // API pour ajouter un logement
    @PostMapping("/add-logement")
    public Logement addLogement(@RequestBody Logement logement) {
        return logementRepository.save(logement);
    }
    
    // API pour consulter un logement
    @GetMapping("/{id}/edit")
    public ResponseEntity<Logement> getLogementById(@PathVariable Long id) {
    	Optional<Logement> logement = logementRepository.findById(id);
        
        if (logement.isPresent()) {
            return ResponseEntity.ok(logement.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // API pour modifier un logement
    @PutMapping("/{id}/update")
    public ResponseEntity<Logement> updateLogement(@PathVariable Long id, @RequestBody Logement logementDetails) {
        
        Optional<Logement> optionalLogement = logementRepository.findById(id);

        if (optionalLogement.isPresent()) {
            Logement existingLogement = optionalLogement.get();
            existingLogement.setResume(logementDetails.getResume());
            existingLogement.setDescription(logementDetails.getDescription());
            existingLogement.setPrice(logementDetails.getPrice());
            existingLogement.setArea(logementDetails.getArea());
            existingLogement.setLongitude(logementDetails.getLongitude());
            existingLogement.setLatitude(logementDetails.getLatitude());
            existingLogement.setId_proprietaire(logementDetails.getId_proprietaire());
            existingLogement.setType(logementDetails.getType());
            existingLogement.setImagesJson(logementDetails.getImagesJson());
            

            Logement updatedLogement = logementRepository.save(existingLogement);
            return ResponseEntity.ok(updatedLogement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteLogement(@PathVariable Long id) {
        Optional<Logement> deletedLogement = logementService.softDelete(id);

        if (deletedLogement.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
}
