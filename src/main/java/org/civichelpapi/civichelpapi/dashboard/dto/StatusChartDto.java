package org.civichelpapi.civichelpapi.dashboard.dto;

import org.civichelpapi.civichelpapi.report.enums.Status;

public record StatusChartDto(
        Status status,
        long count
) {
}
