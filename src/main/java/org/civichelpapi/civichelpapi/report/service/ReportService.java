package org.civichelpapi.civichelpapi.report.service;

import org.civichelpapi.civichelpapi.report.dto.request.ReportRequest;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.enums.Status;

import java.util.List;

public interface ReportService {

    ReportResponse createReport(Long citizenId, ReportRequest request);
    List<ReportResponse> getCitizenReports(Long citizenId);
//    void updateReportStatus(Long reportId, Status newStatus);
}
