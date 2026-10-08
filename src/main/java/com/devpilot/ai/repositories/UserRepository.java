package com.devpilot.ai.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpilot.ai.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
