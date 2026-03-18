package org.civichelpapi.civichelpapi.util.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI configuration.
 *
 * API docs UI:  http://localhost:8080/swagger-ui.html
 * OpenAPI JSON: http://localhost:8080/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    private static final String BEARER_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI civicHelpOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CivicHelp API")
                        .version("1.0.0")
                        .description("""
                                REST API for the CivicHelp civic issue reporting platform.
                                
                                Roles:
                                - **CITIZEN** – Create and manage own reports
                                - **AUTHORITY** – Manage reports in their city
                                - **NGO** – Offer help on unresolved reports
                                - **ADMIN** – Manage categories, moderate reports, view dashboards
                                
                                Authentication uses JWT Bearer tokens.
                                Obtain a token via POST /api/auth/login and click 'Authorize' to test secured endpoints.
                                """)
                        .contact(new Contact()
                                .name("CivicHelp Team")
                        )
                )
                // Register the global JWT bearer security scheme
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
                                .name(BEARER_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Paste the JWT token obtained from /api/auth/login")
                        )
                );
    }
}
