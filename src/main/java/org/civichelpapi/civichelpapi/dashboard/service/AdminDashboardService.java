package org.civichelpapi.civichelpapi.dashboard.service;

import org.civichelpapi.civichelpapi.dashboard.dto.DashboardSummaryDto;
import org.civichelpapi.civichelpapi.dashboard.dto.SlaMetricsDto;
import org.civichelpapi.civichelpapi.dashboard.dto.StatusChartDto;

import java.util.List;

public interface AdminDashboardService {
    DashboardSummaryDto getSummary();
    List<StatusChartDto> getReportsByStatus();
    SlaMetricsDto getSlaMetrics();
}
