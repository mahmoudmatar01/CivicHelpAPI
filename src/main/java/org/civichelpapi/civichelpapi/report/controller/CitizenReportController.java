package org.civichelpapi.civichelpapi.report.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.dto.request.ReportRequest;
import org.civichelpapi.civichelpapi.report.service.CitizenReportService;
import org.civichelpapi.civichelpapi.report.service.ReportService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.civichelpapi.civichelpapi.common.service.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/citizen/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CITIZEN')")
public class CitizenReportController {

    private final CitizenReportService service;
    private final ReportService reportService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<?> createReport(@RequestPart("data") @Valid ReportRequest request,
                                          @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Long userId = JwtUtil.getUserIdFromContext();

        return ResponseEntity.ok(
                ApiResponse.success(
                        reportService.createReport(userId, request,images))
        );
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyReports() {
        Long userId = JwtUtil.getUserIdFromContext();

        return ResponseEntity.ok(
                ApiResponse.success(
                        reportService.getCitizenReports(userId))
        );
    }

    @PostMapping("/{id}/close")
    public ApiResponse<?> closeReport(@PathVariable Long id) {
        Long userId = JwtUtil.getUserIdFromContext();

        return ApiResponse.success(
                service.closeReport(id, userId)
        );
    }
}
