package com.example.demo.service;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserUpdateRequest;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    String login(UserLoginRequest request);

    UserResponse findUser(Long id);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    String deleteUser(Long id);
}
