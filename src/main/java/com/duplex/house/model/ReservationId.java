package com.duplex.house.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
public class ReservationId implements Serializable {
	private Integer userId;
    private Integer logementId;
    private String codeReservation;
}
