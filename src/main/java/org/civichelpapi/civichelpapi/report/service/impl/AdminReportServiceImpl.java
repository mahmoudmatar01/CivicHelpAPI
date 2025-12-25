package org.civichelpapi.civichelpapi.report.service.impl;


import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.civichelpapi.civichelpapi.report.service.AdminReportService;
import org.springframework.stereotype.Service;

import static org.civichelpapi.civichelpapi.report.helper.ReportHelper.toReportResponse;

@RequiredArgsConstructor
@Service
public class AdminReportServiceImpl implements AdminReportService {

    private final ReportRepository reportRepository;

    @Override
    public ReportResponse rejectReport(Long reportId, String reason) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("Report not found"));

        if (report.getStatus() == Status.CLOSED || report.getStatus() == Status.REJECTED) {
            throw new BusinessException("Report cannot be rejected");
        }

        if (reason == null || reason.isBlank()) {
            throw new BusinessException("Rejection reason is required");
        }

        report.setStatus(Status.REJECTED);
        report.setRejectionReason(reason);

        return toReportResponse(reportRepository.save(report));
    }

}
