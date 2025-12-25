package org.civichelpapi.civichelpapi.report.service;

import org.civichelpapi.civichelpapi.report.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorityDashboardService {
    Page<Report> getMyAssignedReports(
            Long authorityId,
            Pageable pageable
    );
    Page<Report> getUnresolvedReportsInMyCity(
            Long authorityId,
            Pageable pageable
    );
}
