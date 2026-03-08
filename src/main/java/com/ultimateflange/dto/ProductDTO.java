package com.ultimateflange.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String key;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String unit;
    private String category;
    private String material;
    private String specs;
    private String standard;
    private String diameter;
    private String pressure;
    private List<String> features;
    private Long supplierId;
    private String supplierName;
}