package com.duplex.house.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EquipementLogementId implements Serializable{
	private Long equipementId;
    private Long logementId;
    private Integer nombreTotal;
}
