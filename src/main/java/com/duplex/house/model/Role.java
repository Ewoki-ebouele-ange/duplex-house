package com.duplex.house.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
@Where(clause = "deleted_at is null")
public class Role implements GrantedAuthority{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// Utiliser EnumType.STRING pour la lisibilité
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name; // Ex: ROLE_ADMIN, ROLE_USER
    
    private Integer deletedBy;
    
    protected Date deletedAt;
    
    @Override
    public String getAuthority() {
        // Spring Security utilise cette méthode pour obtenir le nom du rôle
        return name.name();
    }

}
