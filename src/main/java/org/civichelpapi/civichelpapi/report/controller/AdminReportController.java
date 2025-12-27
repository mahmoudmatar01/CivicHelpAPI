package org.civichelpapi.civichelpapi.report.controller;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.common.service.JwtUtil;
import org.civichelpapi.civichelpapi.report.dto.request.RejectRequest;
import org.civichelpapi.civichelpapi.report.service.AdminReportService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReportController {

    private final AdminReportService service;

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(
            @PathVariable Long id,
            @RequestBody RejectRequest request) {

        Long adminId = JwtUtil.getUserIdFromContext();
        return ResponseEntity.ok(
                ApiResponse.success(
                        service.rejectReport(adminId,id, request.reason())
        )
        );
    }
}

