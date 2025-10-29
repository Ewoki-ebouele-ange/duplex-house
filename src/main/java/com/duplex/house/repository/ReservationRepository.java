package com.duplex.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duplex.house.model.Reservation;
import com.duplex.house.model.ReservationId;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId>{

}
