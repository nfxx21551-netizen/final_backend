package com.ultimateflange.service;

import com.ultimateflange.dto.RegisterRequest;
import com.ultimateflange.model.User;
import com.ultimateflange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

  public User registerUser(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email already exists");
    }
    
    User user = new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setCompany(request.getCompany());
    user.setIndustry(request.getIndustry());
    user.setUserType(request.getUserType());
    
    // ✅ Set default role if not provided
    if (request.getRole() != null) {
        user.setRole(request.getRole());
    } else {
        user.setRole("USER");  // Default value
    }
    
    return userRepository.save(user);
}

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
