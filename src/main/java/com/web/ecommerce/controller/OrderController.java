package com.web.ecommerce.controller;

import com.web.ecommerce.exception.OrderException;
import com.web.ecommerce.exception.UserException;
import com.web.ecommerce.model.Address;
import com.web.ecommerce.model.Order;
import com.web.ecommerce.model.User;

import com.web.ecommerce.service.OrderService;
import com.web.ecommerce.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
    private OrderService orderService;
	
	@Autowired
    private UserService userService;

    
    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
    		@RequestHeader("Authorization") String jwt) throws UserException{
    	
        User user=userService.findUserProfileByJwt(jwt);
        
        Order order=orderService.createOrder(user, shippingAddress);
        
        return new ResponseEntity<Order>(order,HttpStatus.CREATED);
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(
    		@RequestHeader("Authorization") String jwt) throws UserException{
    	System.out.println("hello"+jwt);
        User user=userService.findUserProfileByJwt(jwt);
        
        List<Order> orders=orderService.usersOrderHistory(user.getId());
        
        return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
    }
    

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long orderId,
    		@RequestHeader("Authorization") String jwt) throws UserException{
        try {
        	User user =userService.findUserProfileByJwt(jwt);
            Order order = orderService.findOrderById(orderId);
            return new ResponseEntity<>(order,HttpStatus.OK);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Order>> usersOrderHistory(@PathVariable Long userId) {
        List<Order> orders = orderService.usersOrderHistory(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/place/{orderId}")
    public ResponseEntity<Order> placedOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.placedOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<Order> confirmedOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.confirmedOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/ship/{orderId}")
    public ResponseEntity<Order> shippedOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.shippedOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/deliver/{orderId}")
    public ResponseEntity<Order> deliveredOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.deliveredOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Order> canceledOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.canceledOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

