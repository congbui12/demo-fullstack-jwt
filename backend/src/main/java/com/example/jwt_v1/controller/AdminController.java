package com.example.jwt_v1.controller;

import com.example.jwt_v1.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public ResponseEntity<MessageDto> getAdminPage() {
        return ResponseEntity.ok(new MessageDto("Only Admin can access this endpoint"));
    }
}
