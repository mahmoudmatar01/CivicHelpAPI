package org.civichelpapi.civichelpapi.auth.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.auth.dto.request.RefreshTokenRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.entity.RefreshToken;
import org.civichelpapi.civichelpapi.auth.jwt.JwtService;
import org.civichelpapi.civichelpapi.auth.repository.RefreshTokenRepository;
import org.civichelpapi.civichelpapi.auth.security.CustomUserDetails;
import org.civichelpapi.civichelpapi.auth.security.CustomUserDetailsService;
import org.civichelpapi.civichelpapi.auth.service.RefreshTokenService;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public RefreshToken createRefreshToken(String userEmail) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                ()-> new NotFoundException("User not found")
        );
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(
                Instant.now().plusMillis( 7 * 24 * 60 * 60 * 1000)
        );

        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new BusinessException("Refresh token expired");
        }
        return token;
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest token) {
        String requestToken = token.refreshToken();

        return refreshTokenRepository
                .findByToken(requestToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    CustomUserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                    String accessToken =
                            jwtService.generateToken(userDetails.getUser());
                    return new AuthResponse(
                            accessToken,
                            requestToken,
                            user.getRole()
                    );
                })
                .orElseThrow(() ->
                        new BusinessException("Refresh token not found")
                );
    }

}
