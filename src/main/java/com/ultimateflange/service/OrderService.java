package com.ultimateflange.service;

import com.ultimateflange.dto.OrderDTO;
import com.ultimateflange.model.Order;
import com.ultimateflange.model.OrderStatus;
import com.ultimateflange.model.User;
import com.ultimateflange.repository.OrderRepository;
import com.ultimateflange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {

        log.info("Creating new order for product: {}", orderDTO.getProductName());

        Order order = new Order();

        // Generate order id
        String orderId = generateOrderId();
        order.setOrderId(orderId);

        // Supplier set
        if (orderDTO.getSupplierId() != null) {

            User supplier = userRepository.findById(orderDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));

            order.setSupplier(supplier);
            order.setSupplierName(supplier.getCompany());
        }

        // Customer details
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerCompany(orderDTO.getCustomerCompany());
        order.setCustomerPhone(orderDTO.getCustomerPhone());

        // Product details
        order.setProductKey(orderDTO.getProductKey());
        order.setProductName(orderDTO.getProductName());
        order.setQuantity(orderDTO.getQuantity());
        order.setSize(orderDTO.getSize());
        order.setMaterial(orderDTO.getMaterial());
        order.setSpecs(orderDTO.getSpecs());

        // Address
        order.setAddress(orderDTO.getAddress());
        order.setContactMethod(orderDTO.getContactMethod());

        // Price
        order.setAmount(orderDTO.getAmount());

        // Status
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(new Date());

        // Estimated delivery
        Date estimatedDelivery = new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000));
        order.setEstimatedDelivery(estimatedDelivery);

        // Save order
        Order savedOrder = orderRepository.save(order);

        log.info("Order saved with ID: {}", savedOrder.getOrderId());

        // Send email
        try {
            emailService.sendOrderNotificationToAdmin(savedOrder);
            log.info("Email notification sent for order: {}", savedOrder.getOrderId());
        } catch (Exception e) {
            log.error("Email failed but order saved: {}", e.getMessage());
        }

        return savedOrder;
    }


    // Generate unique order ID
    private String generateOrderId() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());

        Random random = new Random();
        int randomPart = 1000 + random.nextInt(9000);

        return "ORD-" + datePart + "-" + randomPart;
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


    public Order getOrderByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }


    public List<Order> getOrdersBySupplier(Long supplierId) {

        User supplier = userRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        return orderRepository.findBySupplier(supplier);
    }


    public List<Order> getOrdersByCustomer(String customerEmail) {
        return orderRepository.findByCustomerEmail(customerEmail);
    }


    @Transactional
    public Order updateOrderStatus(Long id, String status) {

        Order order = getOrderById(id);

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));

        return orderRepository.save(order);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
}
