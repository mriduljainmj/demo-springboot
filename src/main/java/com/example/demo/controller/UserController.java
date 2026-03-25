package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/create")
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return service.createUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request) {
        return service.login(request);
    }

    @GetMapping("/{id}")
    public UserResponse find(@PathVariable Long id) {
        return service.findUser(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return service.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}