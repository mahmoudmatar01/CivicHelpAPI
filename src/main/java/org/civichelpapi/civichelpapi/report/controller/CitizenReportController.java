package org.civichelpapi.civichelpapi.report.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.dto.request.ReportRequest;
import org.civichelpapi.civichelpapi.report.service.CitizenReportService;
import org.civichelpapi.civichelpapi.report.service.ReportService;
import org.civichelpapi.civichelpapi.shared.model.ApiResponse;
import org.civichelpapi.civichelpapi.shared.service.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/citizen/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CITIZEN')")
public class CitizenReportController {

    private final CitizenReportService service;
    private final ReportService reportService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody @Valid ReportRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        reportService.createReport(jwtUtil.getUserIdFromContext(), request))
        );
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyReports() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        reportService.getCitizenReports(jwtUtil.getUserIdFromContext()))
        );
    }

    @PostMapping("/{id}/close")
    public ApiResponse<?> closeReport(@PathVariable Long id) {

        Long citizenId = jwtUtil.getUserIdFromContext();
        return ApiResponse.success(
                service.closeReport(id, citizenId)
        );
    }
}
