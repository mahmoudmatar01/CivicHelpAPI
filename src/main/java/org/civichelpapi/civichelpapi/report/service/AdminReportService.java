package org.civichelpapi.civichelpapi.report.service;

import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;

public interface AdminReportService {
    ReportResponse rejectReport(Long adminId,Long reportId, String reason);
}
