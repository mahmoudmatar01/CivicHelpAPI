package org.civichelpapi.civichelpapi.audit.dto;

import java.time.LocalDateTime;

public record AuditLogDto(
        String actorId,
        String actorRole,
        String action,
        String entityType,
        String entityId,
        String oldValue,
        String newValue,
        LocalDateTime timestamp
) {
}
