package com.web.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ecommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
