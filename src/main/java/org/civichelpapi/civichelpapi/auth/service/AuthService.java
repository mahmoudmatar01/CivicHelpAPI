package org.civichelpapi.civichelpapi.auth.service;

import org.civichelpapi.civichelpapi.auth.dto.request.LoginRequest;
import org.civichelpapi.civichelpapi.auth.dto.request.RegisterRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthenticatedUserResponse;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthenticatedUserResponse getCurrentUser(Long userId);

}
