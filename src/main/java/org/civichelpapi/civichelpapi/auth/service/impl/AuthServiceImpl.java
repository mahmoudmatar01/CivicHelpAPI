package org.civichelpapi.civichelpapi.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.auth.dto.request.LoginRequest;
import org.civichelpapi.civichelpapi.auth.dto.request.RegisterRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthenticatedUserResponse;
import org.civichelpapi.civichelpapi.auth.entity.RefreshToken;
import org.civichelpapi.civichelpapi.auth.jwt.JwtService;
import org.civichelpapi.civichelpapi.auth.security.CustomUserDetails;
import org.civichelpapi.civichelpapi.auth.security.CustomUserDetailsService;
import org.civichelpapi.civichelpapi.auth.service.AuthService;
import org.civichelpapi.civichelpapi.auth.service.RefreshTokenService;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.enums.Role;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email already in use");
        }

        if (!request.password().equals(request.confirmPassword())){
            throw new BusinessException("Passwords are not match");
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        user.setRole(Role.ROLE_CITIZEN);
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BusinessException("Invalid email or password");
        }

        CustomUserDetails user =
                userDetailsService.loadUserByUsername(request.email());

        String accessToken = jwtService.generateToken(user.getUser());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getUsername());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Invalid credentials");
        }

        if (!user.isEnabled()) {
            throw new BusinessException("Your account has been disabled. Please contact support.");
        }

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                Role.valueOf(user.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority())
        );
    }

    @Override
    public AuthenticatedUserResponse getCurrentUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return new AuthenticatedUserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
