package org.civichelpapi.civichelpapi.auth.service;

public interface TokenBlacklistService {

    void revokeToken(String token);
    boolean isTokenRevoked(String token);
}
