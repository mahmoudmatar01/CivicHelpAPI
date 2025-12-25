package org.civichelpapi.civichelpapi.shared.service;

import org.civichelpapi.civichelpapi.auth.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;


public class JwtUtil {

    public static Long getUserIdFromContext() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails user =
                (CustomUserDetails) Objects.requireNonNull(authentication).getPrincipal();

        return Objects.requireNonNull(user).getUserId();
    }

}
