package com.duplex.house.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "logements")
@Data
@Where(clause = "deleted_at is null")
public class Logement extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	private String resume;
    private String description;
    private double price;
    private double area;
    private double longitude;
    private double latitude;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proprietaire") // Nom de la colonne de clé étrangère
    private User id_proprietaire;
    
    @Enumerated(EnumType.STRING)
    private TypeLogement type; // Utilisation de l'énumération
    
    
    // Le champ pour le tableau JSON (ex: un tableau d'URLs d'images)
    @Column(columnDefinition = "JSON")
    private String imagesJson; 
	
	private Integer deletedBy;
    
    protected Date deletedAt;
    
    @OneToMany(mappedBy = "logementId", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();
    
    @OneToMany(mappedBy = "logementId", cascade = CascadeType.ALL)
    private Set<PieceLogement> piecesLogement = new HashSet<>();
    
    @OneToMany(mappedBy = "logementId", cascade = CascadeType.ALL)
    private Set<EquipementLogement> equipementsLogement = new HashSet<>();
	
}
