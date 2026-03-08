package com.ultimateflange.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String company;
    
    private String industry;
    
    @Column(nullable = false, length = 50)
    private String userType; // "partner" or "supplier"
    
    // ✅ ADD THIS FIELD - 'role' column ke liye
    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'USER'")
    private String role = "USER";  // Default value set karo
    
    private String phone;
    
    private String address;
    
    @Column(updatable = false)
    private java.util.Date createdAt;
    
    private java.util.Date updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = new java.util.Date();
        updatedAt = new java.util.Date();
        if (role == null) {
            role = "USER";  // Ensure role has default value
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new java.util.Date();
    }
}
