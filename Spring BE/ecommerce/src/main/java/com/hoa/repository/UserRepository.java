package com.hoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoa.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
	
}
