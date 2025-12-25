package org.civichelpapi.civichelpapi.report.helper;

import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;


public class ReportHelper {

    public static ReportResponse toReportResponse(Report r) {
        return new ReportResponse(
                r.getId(),
                r.getCategory().getName(),
                r.getDistrict().getName(),
                r.getDistrict().getCity().getName(),
                r.getDistrict().getCity().getGovernorate().getName(),
                r.getDescription(),
                r.getImageUrls(),
                r.getStatus().name(),
                r.getPriority().name(),
                r.getCreatedAt()
        );
    }
}
