package org.civichelpapi.civichelpapi.audit.controller;


import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.audit.dto.AuditLogDto;
import org.civichelpapi.civichelpapi.audit.service.AuditService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/audit-logs")
@PreAuthorize("hasRole('ADMIN')")
public class AuditLogController {

    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<?> getAuditLogs(
            @RequestParam String entityType,
            @RequestParam String entityId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        auditService.findAuditLogsByEntityTypeAndEntityID(entityType,entityId,pageable)
                                .stream()
                                .map(auditLog -> new AuditLogDto(
                                        auditLog.getActorId(),
                                        auditLog.getActorRole(),
                                        auditLog.getAction(),
                                        auditLog.getEntityType(),
                                        auditLog.getEntityId(),
                                        auditLog.getOldValue(),
                                        auditLog.getNewValue(),
                                        auditLog.getTimestamp()
                                ))
                )
        );
    }


}
