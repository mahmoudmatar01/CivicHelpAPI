package org.civichelpapi.civichelpapi.common.service;

import org.civichelpapi.civichelpapi.auth.security.CustomUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class JwtUtil {

    private JwtUtil() {
        // Utility class — do not instantiate
    }

    public static Long getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails user)) {
            throw new AccessDeniedException("User not authenticated or invalid principal type");
        }

        return user.getUserId();
    }

}
