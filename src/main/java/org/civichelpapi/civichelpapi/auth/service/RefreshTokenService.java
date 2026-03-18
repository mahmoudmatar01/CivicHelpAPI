package org.civichelpapi.civichelpapi.auth.service;


import org.civichelpapi.civichelpapi.auth.dto.request.RefreshTokenRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String userEmail);
    RefreshToken verifyExpiration(RefreshToken token);
    AuthResponse refreshToken(RefreshTokenRequest token);

}
