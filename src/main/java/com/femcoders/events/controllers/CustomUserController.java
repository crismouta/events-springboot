package com.femcoders.events.controllers;

import com.femcoders.events.dtos.user.CustomUserRequest;
import com.femcoders.events.dtos.user.CustomUserResponse;
import com.femcoders.events.services.CustomUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CustomUserController {
    public final CustomUserService customUserService;

    public CustomUserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping
    public ResponseEntity<List<CustomUserResponse>> getAllUsers() {
        return new ResponseEntity<>(customUserService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomUserResponse> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(customUserService.getUserById(id), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CustomUserResponse> addUser (@Valid @RequestBody CustomUserRequest userRequest) {
        CustomUserResponse newUser = customUserService.addUser(userRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
