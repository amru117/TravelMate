package com.project.TravelMate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.TravelMate.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByPhoneNo(String phoneNo);
    Optional<User> findByEmail(String email);

}
