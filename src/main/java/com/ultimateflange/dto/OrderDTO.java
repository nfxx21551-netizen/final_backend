package com.ultimateflange.dto;

import com.ultimateflange.model.OrderStatus;
import lombok.Data;

@Data
public class OrderDTO {
    private String id;
    private String orderId;
    private Long supplierId;
    private String supplierName;
    private String customerEmail;
    private String customerName;
    private String customerCompany;
    private String customerPhone;
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
}