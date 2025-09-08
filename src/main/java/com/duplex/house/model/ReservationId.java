package com.duplex.house.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ReservationId implements Serializable {
	private Long userId;
    private Long logementId;
    private Date dateReservation;
}
