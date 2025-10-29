package com.duplex.house.model;

import java.time.LocalTime;
import java.util.Date;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reservations")
@Data
@Where(clause = "deleted_at is null")
public class Reservation extends BaseEntity {
	
	@EmbeddedId
    private ReservationId id;

	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
	@JsonBackReference("User-reservation")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("logementId")
    @JoinColumn(name = "logement_id")
    @JsonBackReference("Logement-reservation")
    private Logement logementId;
    
    // Champs supplémentaires
    private Date dateReservation;
    private double totalPrice;
    private Date dateDebut;
    private Date dateFin;
    private Integer nombreJours;
    private boolean statutPaiement;
    private Integer nombrePersonne;
    private LocalTime heureArrivee;
    private LocalTime heureSortie;
    
    private Integer deletedBy;
    
    protected Date deletedAt;
	
}
