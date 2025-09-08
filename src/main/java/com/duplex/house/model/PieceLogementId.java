package com.duplex.house.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class PieceLogementId implements Serializable{
	private Long pieceId;
    private Long logementId;
    private Integer nombreTotal;
}
