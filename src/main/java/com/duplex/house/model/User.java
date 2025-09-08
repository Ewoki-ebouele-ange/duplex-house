package com.duplex.house.model;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@Where(clause = "deleted_at is null")
public class User extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    private String nom;
    private String prenom;
    
    @Column(unique = true)
    private String email;
    private String motDePasse;
    private String ville;
    private String phoneNumber;
    private String imgProfilPath;
    private String pseudo;
    private String bio;
    private Integer deletedBy;
    
    protected Date deletedAt;
    
    @OneToMany(mappedBy = "id_proprietaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Logement> logements;
    
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();
}
