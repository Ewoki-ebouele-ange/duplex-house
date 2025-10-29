package com.duplex.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duplex.house.model.Logement;

public interface LogementRepository extends JpaRepository<Logement, Integer>{

}
