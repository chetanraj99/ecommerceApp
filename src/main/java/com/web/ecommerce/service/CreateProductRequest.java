package com.web.ecommerce.service;

import java.util.HashSet;
import java.util.Set;

import com.web.ecommerce.model.Size;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateProductRequest {

	private String title;
	private String description;
	private int price;
	private int discountPrive;
	
	private int discountPercent;
	private int quantity;
	private String brand;
	private String color;
	private Set<Size> size=new HashSet<>();
	private String imageUrl;
	
	private String topLavelCategory;
	private String secondLavelCategory;
	private String thirdLavelCategory;

}
