package org.civichelpapi.civichelpapi.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {}