package org.civichelpapi.civichelpapi.report.service;

import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;

public interface CitizenReportService {
    ReportResponse closeReport(Long reportId, Long citizenId);
}
