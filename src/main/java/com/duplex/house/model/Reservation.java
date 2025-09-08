package com.duplex.house.model;

import java.time.LocalTime;
import java.util.Date;

import org.hibernate.annotations.Where;

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
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("logementId")
    @JoinColumn(name = "logement_id")
    private Logement logementId;
    
    // Champs supplémentaires
    private String codeReservation;
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
