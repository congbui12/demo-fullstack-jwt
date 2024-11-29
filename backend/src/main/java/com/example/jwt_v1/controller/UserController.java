package com.example.jwt_v1.controller;

import com.example.jwt_v1.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public ResponseEntity<MessageDto> getUserPage() {
        return ResponseEntity.ok(new MessageDto("Only User can access this endpoint"));
    }
}
