package org.civichelpapi.civichelpapi.report.service;

import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;

public interface AuthorityReportService {
    ReportResponse assignReport(Long reportId, Long authorityId);
    ReportResponse startProgress(Long reportId, Long authorityId);
    ReportResponse resolveReport(Long reportId, Long authorityId, String note);
}
