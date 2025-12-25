package org.civichelpapi.civichelpapi.report.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ReportResponse(
        Long id,
        String categoryName,
        String governorateName,
        String cityName,
        String districtName,
        String description,
        List<String> imageUrls,
        String status,
        String priority,
        LocalDateTime createdAt
) {
}
