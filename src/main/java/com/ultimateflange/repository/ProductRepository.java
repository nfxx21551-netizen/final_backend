package com.ultimateflange.repository;

import com.ultimateflange.model.Product;
import com.ultimateflange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySupplier(User supplier);
    List<Product> findBySupplierId(Long supplierId);
    Product findByProductKey(String productKey);
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}