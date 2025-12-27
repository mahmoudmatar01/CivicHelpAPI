package org.civichelpapi.civichelpapi.report.controller;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.dto.request.ResolutionRequest;
import org.civichelpapi.civichelpapi.report.service.AuthorityReportService;
import org.civichelpapi.civichelpapi.common.response.ApiResponse;
import org.civichelpapi.civichelpapi.common.service.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authority/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('AUTHORITY')")
public class AuthorityReportController {

    private final AuthorityReportService service;

    @PostMapping("/{id}/assign")
    public ApiResponse<?> assign(@PathVariable Long id) {
        Long authorityId = JwtUtil.getUserIdFromContext();
        return ApiResponse.success(service.assignReport(id, authorityId));
    }

    @PostMapping("/{id}/start")
    public ApiResponse<?> start(@PathVariable Long id) {
        Long authorityId = JwtUtil.getUserIdFromContext();
        return ApiResponse.success(service.startProgress(id, authorityId));
    }

    @PostMapping("/{id}/resolve")
    public ApiResponse<?> resolve(
            @PathVariable Long id,
            @RequestBody ResolutionRequest request) {

        Long authorityId = JwtUtil.getUserIdFromContext();
        return ApiResponse.success(
                service.resolveReport(id, authorityId, request.note()));
    }
}
