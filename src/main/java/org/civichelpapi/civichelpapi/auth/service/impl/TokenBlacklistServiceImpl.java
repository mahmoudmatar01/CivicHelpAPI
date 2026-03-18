package org.civichelpapi.civichelpapi.auth.service.impl;

import lombok.RequiredArgsConstructor;

import org.civichelpapi.civichelpapi.auth.entity.RevokedToken;
import org.civichelpapi.civichelpapi.auth.repository.RevokedTokenRepository;
import org.civichelpapi.civichelpapi.auth.service.TokenBlacklistService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final RevokedTokenRepository revokedTokenRepository;

    @Override
    public void revokeToken(String token) {
        RevokedToken revokedToken = new RevokedToken();

        revokedToken.setToken(token);
        revokedToken.setRevokedAt(LocalDateTime.now());

        revokedTokenRepository.save(revokedToken);
    }

    @Override
    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }
}
