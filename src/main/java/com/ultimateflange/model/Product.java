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

    private String productKey; // e.g., "lwn", "wnf"


    private String name;


    private String description;

    private Double price;

    private Integer stock;

    private String unit; // "pieces", "kg", etc.

    private String category;

    private String material;

    private String specs;

    private String standard;

    private String diameter;

    private String pressure;

    private String imageUrl;


    private String features; // JSON string or comma-separated


    private User supplier;

  
    private java.util.Date createdAt;

    private java.util.Date updatedAt;


    protected void onCreate() {
        createdAt = new java.util.Date();
        updatedAt = new java.util.Date();
    }


    protected void onUpdate() {
        updatedAt = new java.util.Date();
    }

}
