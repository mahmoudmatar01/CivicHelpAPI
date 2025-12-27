package org.civichelpapi.civichelpapi.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.auth.dto.request.LoginRequest;
import org.civichelpapi.civichelpapi.auth.dto.request.RegisterRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.service.impl.AuthServiceImpl;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request)));
    }
}
