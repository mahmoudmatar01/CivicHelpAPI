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

    @Autowired
    private UserRepository userRepository;

    @Test
    void register_ShouldCreateCitizenOnly_EvenIfPrivilegedRoleRequested() {
        RegisterRequest request = new RegisterRequest(
                "Test User",
                "test@example.com",
                "password123"
        );

        AuthResponse response = authService.register(request);

        assertEquals(Role.CITIZEN, response.role());
        assertTrue(userRepository.existsByEmail("test@example.com"));
    }

    @Test
    void login_ShouldWorkForValidCredentials() {
        authService.register(
                new RegisterRequest("Login User", "login@example.com", "securePass123")
        );

        AuthResponse response = authService.login(
                new LoginRequest("login@example.com", "securePass123")
        );

        assertNotNull(response.token());
        assertEquals(Role.CITIZEN, response.role());
    }

    @Test
    void login_ShouldFailForInvalidCredentials() {
        authService.register(new RegisterRequest("User", "user@example.com", "pass"));

        assertThrows(BusinessException.class, () ->
            authService.login(new LoginRequest("user@example.com", "wrongPass"))
        );
    }
}
