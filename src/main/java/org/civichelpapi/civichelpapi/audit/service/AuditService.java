package org.civichelpapi.civichelpapi.audit.service;

import org.civichelpapi.civichelpapi.audit.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditService {

    void log(
            Long userId,
            String action,
            String entityType,
            String entityId,
            String oldValue,
            String newValue
    );

    Page<AuditLog> findAuditLogsByEntityTypeAndEntityID(String entityType, String entityId, Pageable pageable);
}
