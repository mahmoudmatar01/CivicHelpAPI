package org.civichelpapi.civichelpapi.auth.dto.response;

import org.civichelpapi.civichelpapi.user.enums.Role;

public record AuthenticatedUserResponse(
        Long id,
        String name,
        String email,
        Role role
) {
}
