package org.civichelpapi.civichelpapi.audit.service.impl;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.audit.dto.AuditLogDto;
import org.civichelpapi.civichelpapi.audit.entity.AuditLog;
import org.civichelpapi.civichelpapi.audit.repository.AuditLogRepository;
import org.civichelpapi.civichelpapi.audit.service.AuditService;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Override
    public void log(Long userId, String action, String entityType, String entityId, String oldValue, String newValue) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        AuditLog log = AuditLog.builder()
                .action(action)
                .actorId(String.valueOf(user.getId()))
                .actorRole(user.getRole().name())
                .entityId(entityId)
                .entityType(entityType)
                .oldValue(oldValue)
                .newValue(newValue)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    @Override
    public Page<AuditLog> findAuditLogsByEntityTypeAndEntityID(String entityType, String entityId, Pageable pageable) {

        return auditLogRepository
                .findByEntityTypeAndEntityId(entityType, entityId, pageable);

    }
}
