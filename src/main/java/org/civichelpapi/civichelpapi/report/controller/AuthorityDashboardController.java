package org.civichelpapi.civichelpapi.report.controller;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.helper.ReportHelper;
import org.civichelpapi.civichelpapi.report.service.AuthorityDashboardService;
import org.civichelpapi.civichelpapi.common.service.JwtUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authority/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('AUTHORITY')")
public class AuthorityDashboardController {

    private final AuthorityDashboardService dashboardService;

    @GetMapping("/assigned")
    public ResponseEntity<?> myAssignedReports(
            Pageable pageable
    ) {
        Long authorityId = JwtUtil.getUserIdFromContext();
        return ResponseEntity.ok(
                dashboardService.getMyAssignedReports(authorityId, pageable)
                        .map(ReportHelper::toReportResponse)
        );
    }

    @GetMapping("/unresolved")
    public ResponseEntity<?> unresolvedInMyCity(
            Pageable pageable
    ) {
        Long authorityId = JwtUtil.getUserIdFromContext();
        return ResponseEntity.ok(
                dashboardService.getUnresolvedReportsInMyCity(authorityId, pageable)
                        .map(ReportHelper::toReportResponse)
        );
    }

}
