package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Create
    public UserResponse createUser(UserCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        repo.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    // Login
    public String login(UserLoginRequest request) {
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return "Login successful!";
    }

    // Find user
    public UserResponse findUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    // Update
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setPassword(request.getPassword()); // hash in real app

        repo.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    // Delete
    public String deleteUser(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        repo.deleteById(id);
        return "User deleted successfully";
    }
}