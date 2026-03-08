package com.ultimateflange.service;

import com.ultimateflange.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Send order notification to admin
     */
    public void sendOrderNotificationToAdmin(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(adminEmail);
            message.setSubject("🔔 New Order Received - " + order.getOrderId());

            String emailBody = buildOrderEmailBody(order);
            message.setText(emailBody);

            mailSender.send(message);
            log.info("✅ Order notification sent to admin for order: {}", order.getOrderId());

        } catch (Exception e) {
            log.error("❌ Failed to send email: {}", e.getMessage());
            // Don't throw exception - order is already saved
        }
    }

    /**
     * Send order confirmation to customer
     */
    public void sendOrderConfirmationToCustomer(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(order.getCustomerEmail());
            message.setSubject("✅ Order Confirmation - " + order.getOrderId());

            String emailBody = buildCustomerEmailBody(order);
            message.setText(emailBody);

            mailSender.send(message);
            log.info("✅ Confirmation email sent to customer: {}", order.getCustomerEmail());

        } catch (Exception e) {
            log.error("❌ Failed to send customer email: {}", e.getMessage());
        }
    }

    /**
     * Build email body for admin
     */
    private String buildOrderEmailBody(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("           🔔 NEW ORDER RECEIVED          \n");
        sb.append("==========================================\n\n");

        sb.append("Order ID: ").append(order.getOrderId()).append("\n");
        sb.append("Date: ").append(order.getOrderDate()).append("\n\n");

        sb.append("--- CUSTOMER DETAILS ---\n");
        sb.append("Name: ").append(order.getCustomerName()).append("\n");
        sb.append("Company: ").append(order.getCustomerCompany()).append("\n");
        sb.append("Email: ").append(order.getCustomerEmail()).append("\n");
        sb.append("Phone: ").append(order.getCustomerPhone()).append("\n\n");

        sb.append("--- ORDER DETAILS ---\n");
        sb.append("Product: ").append(order.getProductName()).append("\n");
        sb.append("Quantity: ").append(order.getQuantity()).append(" pieces\n");
        sb.append("Size: ").append(order.getSize()).append("\n");
        sb.append("Material: ").append(order.getMaterial()).append("\n");
        sb.append("Amount: ₹").append(String.format("%.2f", order.getAmount())).append("\n\n");

        sb.append("--- DELIVERY ADDRESS ---\n");
        sb.append(order.getAddress()).append("\n\n");

        sb.append("--- ADDITIONAL INFO ---\n");
        sb.append("Contact Method: ").append(order.getContactMethod()).append("\n");
        sb.append("Specifications: ").append(order.getSpecs()).append("\n\n");

        sb.append("Supplier: ").append(order.getSupplierName()).append("\n");
        sb.append("Estimated Delivery: ").append(order.getEstimatedDelivery()).append("\n\n");

        sb.append("==========================================\n");

        return sb.toString();
    }

    /**
     * Build email body for customer
     */
    private String buildCustomerEmailBody(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(order.getCustomerName()).append(",\n\n");
        sb.append("Thank you for your order with Ultimate Flange.\n\n");

        sb.append("Order Details:\n");
        sb.append("--------------\n");
        sb.append("Order ID: ").append(order.getOrderId()).append("\n");
        sb.append("Product: ").append(order.getProductName()).append("\n");
        sb.append("Quantity: ").append(order.getQuantity()).append(" pieces\n");
        sb.append("Total Amount: ₹").append(String.format("%.2f", order.getAmount())).append("\n");
        sb.append("Supplier: ").append(order.getSupplierName()).append("\n\n");

        sb.append("Your order has been sent to the supplier. They will contact you within 24 hours.\n\n");

        sb.append("Best regards,\n");
        sb.append("Ultimate Flange Team\n");

        return sb.toString();
    }
}