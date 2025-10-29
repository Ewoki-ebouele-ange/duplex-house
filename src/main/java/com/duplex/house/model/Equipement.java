package com.duplex.house.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipements")
@Data
@Where(clause = "deleted_at is null")
public class Equipement extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nom;
	
	private String description;
	
	private Integer deletedBy;
    
    protected Date deletedAt;
    
    @OneToMany(mappedBy = "equipementId", cascade = CascadeType.ALL)
    private Set<EquipementLogement> equipementLogement = new HashSet<>();
	
}
