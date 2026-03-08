package com.ultimateflange.controller;

import com.ultimateflange.dto.ApiResponse;
import com.ultimateflange.dto.ProductDTO;
import com.ultimateflange.model.Product;
import com.ultimateflange.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> dtos = products.stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(ApiResponse.success(productService.convertToDTO(product)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<?> getProductByKey(@PathVariable String key) {
        Product product = productService.getProductByKey(key);
        if (product == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Product not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(productService.convertToDTO(product)));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<?> getProductsBySupplier(@PathVariable Long supplierId) {
        try {
            List<Product> products = productService.getProductsBySupplier(supplierId);
            List<ProductDTO> dtos = products.stream()
                    .map(productService::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(dtos));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Get supplier ID from user details (you might need to fetch from DB)
            // For now, we'll use a placeholder
            Long supplierId = 1L;
            Product product = productService.createProduct(productDTO, supplierId);
            return ResponseEntity.ok(ApiResponse.success("Product created", productService.convertToDTO(product)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product product = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(ApiResponse.success("Product updated", productService.convertToDTO(product)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(ApiResponse.success("Product deleted", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String q) {
        List<Product> products = productService.searchProducts(q);
        List<ProductDTO> dtos = products.stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
}