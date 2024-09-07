package com.web.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.ecommerce.exception.ProductException;
import com.web.ecommerce.model.Category;
import com.web.ecommerce.model.Product;
import com.web.ecommerce.repository.CategoryRepository;
import com.web.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Product createProduct(CreateProductRequest req) {
		Category topLevel=categoryRepository.findByName(req.getTopLevelCategory());
		if(topLevel==null) {
			Category topLevelCategory=new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			topLevel=categoryRepository.save(topLevelCategory);
		}
		System.out.println(topLevel);
		Category secondLevel=categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
		if(secondLevel==null) {
			Category secondLevelCategory=new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);
			secondLevel=categoryRepository.save(secondLevelCategory);
		}
		
		Category thirdLevel=categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
		if(thirdLevel==null) {
			Category thirdLevelCategory=new Category();
			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			thirdLevelCategory.setLevel(3);
			thirdLevel=categoryRepository.save(thirdLevelCategory);
		}
		
		Product product=new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDiscription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPercent(req.getDiscountPercent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct=productRepository.save(product);
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product=findProductById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product deleted successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product=findProductById(productId);
		
		if(req.getQuantity()!=0)
			product.setQuantity(req.getQuantity());
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt=productRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		throw new ProductException("Product is not found with id "+id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> size, Integer minPrice,
	                                    Integer maxPrice, Integer minDiscount, String sort, String stock,
	                                    Integer pageNumber, Integer pageSize) {
	    
	    // Create Pageable object with default sorting
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    
	    // Fetch filtered products from repository
	    List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

	    // Debugging printout for fetched products
	    for (Product product : products) {
	        System.out.println(product);
	    }
	    
	    // Apply color filter
	    if (colors != null && !colors.isEmpty()) {
	        products = products.stream()
	                .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
	                .collect(Collectors.toList());
	    }
	    
	    // Apply stock filter
	    if (stock != null) {
	        if (stock.equals("in_stock")) {
	            products = products.stream()
	                    .filter(p -> p.getQuantity() > 0)
	                    .collect(Collectors.toList());
	        } else if (stock.equals("out_of_stock")) {
	            products = products.stream()
	                    .filter(p -> p.getQuantity() < 1)
	                    .collect(Collectors.toList());
	        }
	    }
	    
	    // Apply pagination
	    int startIndex = (int) pageable.getOffset();
	    int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
	    
	    // Check for valid indices
	    if (startIndex >= products.size()) {
	        startIndex = products.size();
	        endIndex = products.size();
	    }
	    
	    List<Product> pageContent = products.subList(startIndex, endIndex);
	    
	    // Create and return a Page object
	    Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
	    return filteredProducts;
	}



	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

}
