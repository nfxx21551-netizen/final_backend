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
@Slf4j  // ✅ Add this for logging
public class OrderService {

    private final EmailService emailService;  // ✅ Use final with @RequiredArgsConstructor
    private final OrderRepository orderRepository;  // ✅ Use final
    private final UserRepository userRepository;  // ✅ Use final

    /**
     * Create order from OrderDTO (Main method)
     */
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        log.info("Creating new order for product: {}", orderDTO.getProductName());

        Order order = new Order();

        // Generate custom order ID
        String orderId = generateOrderId();
        order.setOrderId(orderId);

        // Set supplier if provided
        if (orderDTO.getSupplierId() != null) {
            User supplier = userRepository.findById(orderDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + orderDTO.getSupplierId()));
            order.setSupplier(supplier);
        }

        // Set order details
        order.setSupplierName(orderDTO.getSupplierName());
        order.setCustomerEmail(orderDTO.getCustomerEmail());
        order.setCustomerName(orderDTO.getCustomerName());
        order.setCustomerCompany(orderDTO.getCustomerCompany());
        order.setCustomerPhone(orderDTO.getCustomerPhone());
        order.setProductKey(orderDTO.getProductKey());
        order.setProductName(orderDTO.getProductName());
        order.setQuantity(orderDTO.getQuantity());
        order.setSize(orderDTO.getSize());
        order.setMaterial(orderDTO.getMaterial());
        order.setSpecs(orderDTO.getSpecs());
        order.setAddress(orderDTO.getAddress());
        order.setContactMethod(orderDTO.getContactMethod());
        order.setAmount(orderDTO.getAmount());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(new Date());

        // Set estimated delivery (7 days from now)
        Date estimatedDelivery = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
        order.setEstimatedDelivery(estimatedDelivery);

        // Save the order
        Order savedOrder = orderRepository.save(order);
        log.info("✅ Order saved with ID: {}", savedOrder.getOrderId());

        // ✅ Send email notification to admin
        try {
            emailService.sendOrderNotificationToAdmin(savedOrder);
            log.info("✅ Email notification sent for order: {}", savedOrder.getOrderId());
        } catch (Exception e) {
            log.error("❌ Email failed but order saved: {}", e.getMessage());
        }

        return savedOrder;
    }

    /**
     * Alternative method - if you need to create order directly from Order entity
     */
    @Transactional
    public Order createOrder(Order newOrder) {
        log.info("Creating order directly from entity");
        Order savedOrder = orderRepository.save(newOrder);

        try {
            emailService.sendOrderNotificationToAdmin(savedOrder);
            log.info("✅ Email notification sent for order: {}", savedOrder.getOrderId());
        } catch (Exception e) {
            log.error("❌ Email failed but order saved: {}", e.getMessage());
        }

        return savedOrder;
    }

    /**
     * Generate unique order ID
     */
    private String generateOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String datePart = sdf.format(new Date());
        Random random = new Random();
        int randomPart = 1000 + random.nextInt(9000);
        return "ORD-" + datePart + "-" + randomPart;
    }

    /**
     * Get order by database ID
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    /**
     * Get order by custom order ID
     */
    public Order getOrderByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    /**
     * Get orders by supplier ID
     */
    public List<Order> getOrdersBySupplier(Long supplierId) {
        User supplier = userRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierId));
        return orderRepository.findBySupplier(supplier);
    }

    /**
     * Get orders by customer email
     */
    public List<Order> getOrdersByCustomer(String customerEmail) {
        return orderRepository.findByCustomerEmail(customerEmail);
    }

    /**
     * Update order status
     */
    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        Order updatedOrder = orderRepository.save(order);
        log.info("Order {} status updated to: {}", updatedOrder.getOrderId(), status);
        return updatedOrder;
    }

    /**
     * Get all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}