package com.ultimateflange.repository;

import com.ultimateflange.model.Order;
import com.ultimateflange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findBySupplier(User supplier);
    List<Order> findBySupplierId(Long supplierId);
    List<Order> findByCustomerEmail(String customerEmail);
    Order findByOrderId(String orderId);
    List<Order> findByStatus(String status);
}