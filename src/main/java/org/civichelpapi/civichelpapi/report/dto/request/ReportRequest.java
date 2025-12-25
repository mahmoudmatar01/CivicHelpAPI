package org.civichelpapi.civichelpapi.report.dto.request;

import java.util.List;

public record ReportRequest(
        Integer categoryId,
        Integer districtId,
        String description
//        List<String> imagesUrl
) {
}
