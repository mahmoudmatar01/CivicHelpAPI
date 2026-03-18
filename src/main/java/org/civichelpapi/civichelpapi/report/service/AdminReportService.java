package org.civichelpapi.civichelpapi.report.service;

import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;

import java.util.List;

public interface AdminReportService {
    ReportResponse rejectReport(Long adminId,Long reportId, String reason);
    List<ReportResponse> getAllNewReports();
}
