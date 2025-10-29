/**
 * 
 */
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

import com.duplex.house.model.Equipement;
import com.duplex.house.repository.EquipementRepository;
import com.duplex.house.service.EquipementService;

/**
 * 
 */
@RestController
@RequestMapping("/equipements")
public class EquipementController {

	
	@Autowired
    private EquipementRepository equipementRepository;
	
	@Autowired
    private EquipementService equipementService; // Assurez-vous que cette ligne est présente
	
	// API pour consulter la liste des utilisateurs
    @GetMapping("/")
    public List<Equipement> getAllEquipement() {
        return equipementRepository.findAll();
    }
    
    // API pour ajouter une piece
    @PostMapping("/add-equipement")
    public Equipement addEquipement(@RequestBody Equipement equipement) {
        return equipementRepository.save(equipement);
    }
    
 // API pour consulter une piece
    @GetMapping("/{id}/edit")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable Integer id) {
    	Optional<Equipement> equipement = equipementRepository.findById(id);
        
        if (equipement.isPresent()) {
            return ResponseEntity.ok(equipement.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // API pour modifier une piece
    @PutMapping("/{id}/update")
    public ResponseEntity<Equipement> updateEquipement(@PathVariable Integer id, @RequestBody Equipement equipementDetails) {
        
        Optional<Equipement> optionalEquipement = equipementRepository.findById(id);

        if (optionalEquipement.isPresent()) {
            Equipement existingEquipement = optionalEquipement.get();
            existingEquipement.setNom(equipementDetails.getNom());
            existingEquipement.setDescription(equipementDetails.getDescription());

            Equipement updatedEquipement = equipementRepository.save(existingEquipement);
            return ResponseEntity.ok(updatedEquipement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePiece(@PathVariable Integer id) {
        Optional<Equipement> deletedEquipement = equipementService.softDelete(id);

        if (deletedEquipement.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
