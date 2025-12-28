package org.civichelpapi.civichelpapi.report.dto.request;


public record ReportRequest(
        Integer categoryId,
        Integer districtId,
        String description
//        List<String> imagesUrl
) {
}
