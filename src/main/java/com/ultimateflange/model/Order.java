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


    private String orderId;

    private User supplier;

    private String supplierName;

    private String customerEmail;

    private String customerName;

    private String customerCompany;

    private String customerPhone;

     private Long productId;

    private String productKey;

    private String productName;

    private Integer quantity;

    private String size;

    private String material;

    private String specs;

    private String address;

    private String contactMethod;

    private Double amount;

 
    private OrderStatus status;

    private java.util.Date orderDate;

    private java.util.Date estimatedDelivery;

    private String trackingInfo;

    private java.util.Date createdAt;

    private java.util.Date updatedAt;


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


    protected void onUpdate() {
        updatedAt = new java.util.Date();
    }

}
