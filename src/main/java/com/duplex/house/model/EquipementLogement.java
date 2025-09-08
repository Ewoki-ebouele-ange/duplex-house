package com.duplex.house.model;

import java.util.Date;

import org.hibernate.annotations.Where;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "equipements_logements")
@Data
@Where(clause = "deleted_at is null")
public class EquipementLogement extends BaseEntity{
	
	@EmbeddedId
    private EquipementLogementId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipementId")
    @JoinColumn(name = "equipement_id")
    private Equipement equipementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("logementId")
    @JoinColumn(name = "logement_id")
    private Logement logementId;
    
    private String description;
    
    private Integer deletedBy;
    
    protected Date deletedAt;
	
}
