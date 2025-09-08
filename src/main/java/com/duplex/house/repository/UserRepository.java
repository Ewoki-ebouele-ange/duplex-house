package com.duplex.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duplex.house.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
