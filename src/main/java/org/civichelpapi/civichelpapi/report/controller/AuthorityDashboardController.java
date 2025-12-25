package org.civichelpapi.civichelpapi.report.controller;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.service.AuthorityDashboardService;
import org.civichelpapi.civichelpapi.shared.service.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @GetMapping("/assigned")
    public ResponseEntity<?> myAssignedReports(
            Pageable pageable
    ) {
        Long authorityId = jwtUtil.getUserIdFromContext();
        return ResponseEntity.ok(
                dashboardService.getMyAssignedReports(authorityId, pageable)
                        .map(this::map)
        );
    }

    @GetMapping("/unresolved")
    public ResponseEntity<?> unresolvedInMyCity(
            Pageable pageable
    ) {
        Long authorityId = jwtUtil.getUserIdFromContext();
        return ResponseEntity.ok(
                dashboardService.getUnresolvedReportsInMyCity(authorityId, pageable)
                        .map(this::map)
        );
    }

    private ReportResponse map(Report r) {
        return new ReportResponse(
                r.getId(),
                r.getCategory().getName(),
                r.getDistrict().getName(),
                r.getDistrict().getCity().getName(),
                r.getDistrict().getCity().getGovernorate().getName(),
                r.getDescription(),
                r.getImageUrls(),
                r.getStatus().name(),
                r.getPriority().name(),
                r.getCreatedAt()
        );
    }
}
