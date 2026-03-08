package com.ultimateflange.controller;

import com.ultimateflange.dto.ApiResponse;
import com.ultimateflange.dto.OrderDTO;
import com.ultimateflange.model.Order;
import com.ultimateflange.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    // ✅ Bilkul simple - bina kisi annotation ke
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            System.out.println("📦 Order received: " + orderDTO); // Debug log
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(ApiResponse.success("Order created", order));
        } catch (Exception e) {
            e.printStackTrace(); // Debug log
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(orderService.getAllOrders()));
    }
}
