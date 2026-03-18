package org.civichelpapi.civichelpapi.auth.dto.response;

import org.civichelpapi.civichelpapi.user.enums.Role;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Role role
) {}