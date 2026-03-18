package org.civichelpapi.civichelpapi.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RefreshTokenRequest(
        @NotEmpty String refreshToken
) {
}
