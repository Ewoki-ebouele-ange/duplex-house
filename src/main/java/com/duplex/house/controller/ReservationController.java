package com.duplex.house.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duplex.house.model.Reservation;
import com.duplex.house.model.ReservationId;
import com.duplex.house.repository.ReservationRepository;
import com.duplex.house.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
    private ReservationService reservationService;

	
	// API pour consulter une reservation
	@GetMapping
    public ResponseEntity<Reservation> getReservation(
        @RequestParam("userId") Integer userId,
        @RequestParam("logementId") Integer logementId,
        @RequestParam("codeReservation") String codeReservation
    ) {
		ReservationId reservationId = new ReservationId();
        reservationId.setUserId(userId);
        reservationId.setLogementId(logementId);
        reservationId.setCodeReservation(codeReservation);

        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        
        if (reservation.isPresent()) {
            return ResponseEntity.ok(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
	}
    
    // API pour éffectuer une reservation
    @PostMapping("/add-reservation")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = reservationService.createReservation(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }
    
    
}
