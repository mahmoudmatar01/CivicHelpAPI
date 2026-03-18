package org.civichelpapi.civichelpapi.auth.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.auth.dto.request.LoginRequest;
import org.civichelpapi.civichelpapi.auth.dto.request.RefreshTokenRequest;
import org.civichelpapi.civichelpapi.auth.dto.request.RegisterRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.service.AuthService;
import org.civichelpapi.civichelpapi.auth.service.RefreshTokenService;
import org.civichelpapi.civichelpapi.auth.service.TokenBlacklistService;
import org.civichelpapi.civichelpapi.util.response.ApiResponse;
import org.civichelpapi.civichelpapi.util.service.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        authService.login(request)
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        AuthResponse authResponse = refreshTokenService.refreshToken(request);
        return ResponseEntity.ok(
                ApiResponse.success(authResponse)
        );
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.revokeToken(token);
        }

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getCurrentUser() {
        Long userId = JwtUtil.getUserIdFromContext();
        return ResponseEntity.ok(
                ApiResponse.success(
                        authService.getCurrentUser(userId)
                )
        );
    }
}
