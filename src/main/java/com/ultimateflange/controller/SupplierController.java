package com.ultimateflange.controller;

import com.ultimateflange.dto.ApiResponse;
import com.ultimateflange.dto.ProductDTO;
import com.ultimateflange.model.Order;
import com.ultimateflange.model.Product;
import com.ultimateflange.model.User;
import com.ultimateflange.service.OrderService;
import com.ultimateflange.service.ProductService;
import com.ultimateflange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SupplierController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User supplier = userService.findByEmail(userDetails.getUsername());

            List<Product> products = productService.getProductsBySupplier(supplier.getId());
            List<Order> orders = orderService.getOrdersBySupplier(supplier.getId());

            return ResponseEntity.ok(ApiResponse.success(new Object() {
                public final User user = supplier;
                public final List<Product> product = products;
                public final List<Order> order = orders;
                public final int productCount = products.size();
                public final int orderCount = orders.size();
                public final double totalRevenue = orders.stream()
                        .filter(o -> o.getAmount() != null)
                        .mapToDouble(Order::getAmount)
                        .sum();
            }));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}