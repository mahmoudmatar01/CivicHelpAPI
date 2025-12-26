package org.civichelpapi.civichelpapi.dashboard.dto;

public record DashboardSummaryDto(
        long totalReports,
        long openReports,
        long inProgressReports,
        long resolvedReports,
        long rejectedReports,
        long overdueReports
) {
}
