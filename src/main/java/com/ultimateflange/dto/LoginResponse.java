package com.ultimateflange.dto;

import com.ultimateflange.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private User user;
    private String message;
}