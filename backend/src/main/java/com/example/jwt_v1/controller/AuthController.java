package com.example.jwt_v1.controller;

import com.example.jwt_v1.dto.JwtAuthResponse;
import com.example.jwt_v1.dto.LoginRequest;
import com.example.jwt_v1.dto.MessageDto;
import com.example.jwt_v1.dto.SignupRequest;
import com.example.jwt_v1.entity.User;
import com.example.jwt_v1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/welcome")
    public ResponseEntity<MessageDto> home() {
        return ResponseEntity.ok(new MessageDto("Welcome to this public endpoint"));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
