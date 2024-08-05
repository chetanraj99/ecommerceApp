package com.web.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.ecommerce.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
