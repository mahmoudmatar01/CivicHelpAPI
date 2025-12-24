package org.civichelpapi.civichelpapi.auth.dto.request;

import org.civichelpapi.civichelpapi.user.enums.Role;

public record RegisterRequest(
        String fullName,
        String email,
        String password,
        Role role,
        Integer governorateId,
        Integer cityId,
        Integer districtId
) {
}
