package org.civichelpapi.civichelpapi.dashboard.dto;

public record SlaMetricsDto(
        long totalReports,
        long resolvedWithinSla,
        long resolvedLate,
        long unresolvedOverdue,
        double slaComplianceRate
) {
}
