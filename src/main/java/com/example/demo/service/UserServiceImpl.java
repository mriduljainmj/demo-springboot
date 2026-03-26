package com.example.demo.service;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordUtil.hashPassword(request.getPassword()));

        repo.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public String login(UserLoginRequest request) {
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String hashedInput = PasswordUtil.hashPassword(request.getPassword());

        if (!user.getPassword().equals(hashedInput)) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful!";
    }

    @Override
    public UserResponse findUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setPassword(request.getPassword());

        repo.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public String deleteUser(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        repo.deleteById(id);
        return "User deleted successfully";
    }
}
