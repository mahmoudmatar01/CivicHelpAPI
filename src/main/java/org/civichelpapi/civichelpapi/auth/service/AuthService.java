package org.civichelpapi.civichelpapi.auth.service;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.auth.dto.request.LoginRequest;
import org.civichelpapi.civichelpapi.auth.dto.request.RegisterRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.jwt.JwtService;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.location.reposirory.CityRepository;
import org.civichelpapi.civichelpapi.location.reposirory.DistrictRepository;
import org.civichelpapi.civichelpapi.location.reposirory.GovernorateRepository;
import org.civichelpapi.civichelpapi.user.domain.User;
import org.civichelpapi.civichelpapi.user.enums.Role;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final GovernorateRepository governorateRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email already in use");
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setEnabled(true);

        if (request.role() == Role.AUTHORITY) {
            if (request.governorateId() == null) {
                throw new BusinessException("Authority must have a governorate");
            }

            user.setGovernorate(governorateRepository
                    .findById(request.governorateId())
                    .orElseThrow(() -> new BusinessException("Governorate not found")));

            if (request.cityId() != null) {
                user.setCity(cityRepository
                        .findById(request.cityId())
                        .orElseThrow(() -> new BusinessException("City not found")));
            }

            if (request.districtId() != null) {
                user.setDistrict(districtRepository
                        .findById(request.districtId())
                        .orElseThrow(() -> new BusinessException("District not found")));
            }
        }

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getRole());
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getRole());
    }
}
