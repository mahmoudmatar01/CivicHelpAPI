package org.civichelpapi.civichelpapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables Spring Data JPA auditing (createdAt / updatedAt population).
 * This is the single correct place for @EnableJpaAuditing in the application.
 * Previously it was incorrectly placed on the @MappedSuperclass BaseEntity,
 * which is not a Spring configuration class and ignores the annotation.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
