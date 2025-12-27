package org.civichelpapi.civichelpapi.report.controller;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.service.NGOReportService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ngo/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('NGO')")
public class NGOReportController {

    private final NGOReportService service;

    @GetMapping("/unresolved")
    public ResponseEntity<?> unresolvedReports() {
        return ResponseEntity.ok(
                ApiResponse.success(service.getUnresolvedReports())
        );
    }
}
