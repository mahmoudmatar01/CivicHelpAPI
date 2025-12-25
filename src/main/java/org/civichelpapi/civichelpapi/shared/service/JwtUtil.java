package org.civichelpapi.civichelpapi.shared.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class JwtUtil {

    public Long getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("Cannot get authenticated user");
        }
        return Long.valueOf(authentication.getName());
    }

}
