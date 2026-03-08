package com.ultimateflange.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productKey; // e.g., "lwn", "wnf"

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private Double price;

    private Integer stock;

    private String unit; // "pieces", "kg", etc.

    private String category;

    private String material;

    @Column(length = 1000)
    private String specs;

    private String standard;

    private String diameter;

    private String pressure;

    private String imageUrl;

    @Column(length = 1000)
    private String features; // JSON string or comma-separated

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private User supplier;

    @Column(updatable = false)
    private java.util.Date createdAt;

    private java.util.Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new java.util.Date();
        updatedAt = new java.util.Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new java.util.Date();
    }
}