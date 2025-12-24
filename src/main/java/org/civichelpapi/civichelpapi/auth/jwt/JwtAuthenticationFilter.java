package org.civichelpapi.civichelpapi.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.civichelpapi.civichelpapi.auth.security.CustomUserDetails;
import org.civichelpapi.civichelpapi.auth.security.CustomUserDetailsService;
import org.civichelpapi.civichelpapi.user.domain.User;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
//    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService,
//                                   CustomUserDetailsService userDetailsService,
                                   UserRepository userRepository) {
        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtService.extractUserId(token);

        User user = userRepository.findById(userId).orElse(null);

        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = new CustomUserDetails(user);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}

