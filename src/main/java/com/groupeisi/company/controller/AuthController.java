package com.groupeisi.company.controller;

import com.groupeisi.company.dto.AuthRequest;
import com.groupeisi.company.dto.UserAccountDto;
import com.groupeisi.company.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token = authService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
    }

    @PostMapping("/register")
    public ResponseEntity<UserAccountDto> register(@RequestBody UserAccountDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }
}