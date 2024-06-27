package com.hoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoa.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
