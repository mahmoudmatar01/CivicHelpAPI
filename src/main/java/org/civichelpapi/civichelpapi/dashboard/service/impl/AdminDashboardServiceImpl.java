package org.civichelpapi.civichelpapi.dashboard.service.impl;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.dashboard.dto.DashboardSummaryDto;
import org.civichelpapi.civichelpapi.dashboard.dto.SlaMetricsDto;
import org.civichelpapi.civichelpapi.dashboard.dto.StatusChartDto;
import org.civichelpapi.civichelpapi.dashboard.service.AdminDashboardService;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final ReportRepository reportRepository;

    @Override
    public DashboardSummaryDto getSummary() {

        long total = reportRepository.count();
        long open = reportRepository.countByStatus(Status.OPEN);
        long inProgress = reportRepository.countByStatus(Status.IN_PROGRESS);
        long resolved = reportRepository.countByStatus(Status.RESOLVED);
        long rejected = reportRepository.countByStatus(Status.REJECTED);
        long overdue = reportRepository.countOverdueReports(LocalDateTime.now());

        return new DashboardSummaryDto(
                total,
                open,
                inProgress,
                resolved,
                rejected,
                overdue
        );
    }

    @Override
    public List<StatusChartDto> getReportsByStatus() {
        return reportRepository.countReportsByStatus();
    }

    @Override
    public SlaMetricsDto getSlaMetrics() {

        long totalReports = reportRepository.count();
        long resolvedWithinSla = reportRepository.countResolvedWithinSla();
        long resolvedLate = reportRepository.countResolvedLate();
        long unresolvedOverdue = reportRepository.countUnresolvedOverdue();
        long totalResolved = resolvedWithinSla + resolvedLate;
        double complianceRate = totalResolved == 0
                ? 0
                : (resolvedWithinSla * 100.0) / totalResolved;

        return new SlaMetricsDto(
                totalReports,
                resolvedWithinSla,
                resolvedLate,
                unresolvedOverdue,
                Math.round(complianceRate * 100.0) / 100.0
        );
    }
}
