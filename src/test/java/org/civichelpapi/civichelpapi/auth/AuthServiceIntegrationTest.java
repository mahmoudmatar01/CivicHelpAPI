package org.civichelpapi.civichelpapi.auth;

import org.civichelpapi.civichelpapi.auth.dto.request.LoginRequest;
import org.civichelpapi.civichelpapi.auth.dto.request.RegisterRequest;
import org.civichelpapi.civichelpapi.auth.dto.response.AuthResponse;
import org.civichelpapi.civichelpapi.auth.service.AuthService;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.user.enums.Role;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Test
    void login_ShouldFailForInvalidCredentials() {
        authService.register(
                new RegisterRequest(
                "User",
                "user@example.com",
                "pass",
                        "pass"
                )
        );

        assertThrows(BusinessException.class, () ->
            authService.login(new LoginRequest("user@example.com", "wrongPass"))
        );
    }
}
