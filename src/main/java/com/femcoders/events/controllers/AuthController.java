package com.femcoders.events.controllers;

import com.femcoders.events.dtos.user.CustomUserRequest;
import com.femcoders.events.dtos.user.CustomUserResponse;
import com.femcoders.events.dtos.user.JwtResponse;
import com.femcoders.events.security.CustomUserDetail;
import com.femcoders.events.security.jwt.JwtService;
import com.femcoders.events.services.CustomUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final CustomUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(CustomUserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomUserResponse> register(@RequestBody @Valid CustomUserRequest userRequest) {
        CustomUserRequest userRequestWithRolByDefault = new CustomUserRequest(userRequest.username(), userRequest.password(), "ROLE_USER");

        CustomUserResponse userResponse = userService.addUser(userRequestWithRolByDefault);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody CustomUserRequest userRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.username(), userRequest.password()));

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetail);

        JwtResponse jwtResponse = new JwtResponse(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
