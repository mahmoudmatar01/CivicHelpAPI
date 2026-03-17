package org.civichelpapi.civichelpapi.report.helper;

import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;


public class ReportHelper {

    private ReportHelper() {
        // Utility class — do not instantiate
    }

    public static ReportResponse toReportResponse(Report r) {
        return new ReportResponse(
                r.getId(),
                r.getCategory().getName(),
                r.getDistrict().getCity().getGovernorate().getName(),  // governorateName (was wrong before)
                r.getDistrict().getCity().getName(),                   // cityName
                r.getDistrict().getName(),                             // districtName (was wrong before)
                r.getDescription(),
                r.getImageUrls(),
                r.getStatus().name(),
                r.getPriority().name(),
                r.getCreatedAt()
        );
    }
}
