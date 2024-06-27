package com.hoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoa.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
}
