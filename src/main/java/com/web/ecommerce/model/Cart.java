package com.web.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@OneToMany(mappedBy = "cart",cascade=CascadeType.ALL,orphanRemoval = true)
	private Set<CartItem> cartItem=new HashSet();
	
	@Column(name="total_price")
	private double totalPirce;
	
	@Column(name="total_item")
	private int totalItem;
	
	private int totalDiscountedPrice;
	
	private int discount;
	
	
}
