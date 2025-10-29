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

import com.duplex.house.model.Piece;
import com.duplex.house.repository.PieceRepository;
import com.duplex.house.service.PieceService;

/**
 * 
 */
@RestController
@RequestMapping("/pieces")
public class PieceController {
	
	@Autowired
    private PieceRepository pieceRepository;
	
	@Autowired
    private PieceService pieceService; // Assurez-vous que cette ligne est présente
	
	// API pour consulter la liste des utilisateurs
    @GetMapping("/")
    public List<Piece> getAllPiece() {
        return pieceRepository.findAll();
    }
    
 // API pour ajouter une piece
    @PostMapping("/add-piece")
    public Piece addPiece(@RequestBody Piece piece) {
        return pieceRepository.save(piece);
    }
    
 // API pour consulter une piece
    @GetMapping("/{id}/edit")
    public ResponseEntity<Piece> getPieceById(@PathVariable Integer id) {
    	Optional<Piece> piece = pieceRepository.findById(id);
        
        if (piece.isPresent()) {
            return ResponseEntity.ok(piece.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // API pour modifier une piece
    @PutMapping("/{id}/update")
    public ResponseEntity<Piece> updatePiece(@PathVariable Integer id, @RequestBody Piece pieceDetails) {
        
        Optional<Piece> optionalPiece = pieceRepository.findById(id);

        if (optionalPiece.isPresent()) {
            Piece existingPiece = optionalPiece.get();
            existingPiece.setNom(pieceDetails.getNom());
            existingPiece.setDescription(pieceDetails.getDescription());

            Piece updatedPiece = pieceRepository.save(existingPiece);
            return ResponseEntity.ok(updatedPiece);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePiece(@PathVariable Integer id) {
        Optional<Piece> deletedPiece = pieceService.softDelete(id);

        if (deletedPiece.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
