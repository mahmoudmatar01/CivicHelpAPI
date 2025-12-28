package org.civichelpapi.civichelpapi.report.service;

import org.civichelpapi.civichelpapi.report.dto.request.ReportRequest;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReportService {

    ReportResponse createReport(Long citizenId, ReportRequest request,List<MultipartFile> images);
    List<ReportResponse> getCitizenReports(Long citizenId);
//    void updateReportStatus(Long reportId, Status newStatus);
}
