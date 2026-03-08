package com.ultimateflange.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private User supplier;

    private String supplierName;

    private String customerEmail;

    private String customerName;

    private String customerCompany;

    private String customerPhone;

    // ✅ YEH FIELD ADD KARO agar database mein product_id column hai
     @Column(name = "product_id")
     private Long productId;

    private String productKey;

    private String productName;

    private Integer quantity;

    private String size;

    private String material;

    @Column(length = 1000)
    private String specs;

    @Column(length = 1000)
    private String address;

    private String contactMethod;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private java.util.Date orderDate;

    private java.util.Date estimatedDelivery;

    private String trackingInfo;

    @Column(updatable = false)
    private java.util.Date createdAt;

    private java.util.Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new java.util.Date();
        updatedAt = new java.util.Date();
        if (orderDate == null) {
            orderDate = new java.util.Date();
        }
        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new java.util.Date();
    }
}