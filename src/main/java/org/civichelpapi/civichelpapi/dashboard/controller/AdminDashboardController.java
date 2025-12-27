package org.civichelpapi.civichelpapi.dashboard.controller;


import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.dashboard.service.AdminDashboardService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getSummary())
        );
    }

    @GetMapping("/reports-by-status")
    public ResponseEntity<?> getReportsByStatus() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getReportsByStatus())
        );
    }

    @GetMapping("/sla-metrics")
    public ResponseEntity<?> getSlaMetrics() {
        return ResponseEntity.ok(
                ApiResponse.success(dashboardService.getSlaMetrics())
        );
    }
}
