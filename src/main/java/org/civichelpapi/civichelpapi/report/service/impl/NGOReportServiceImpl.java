package org.civichelpapi.civichelpapi.report.service.impl;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.helper.ReportHelper;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.civichelpapi.civichelpapi.report.service.NGOReportService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NGOReportServiceImpl implements NGOReportService {

    private final ReportRepository reportRepository;

    @Override
    public List<ReportResponse> getUnresolvedReports() {
        List<Report> reports =  reportRepository.findByStatusIn();
        return reports.stream().map(
                ReportHelper::toReportResponse
        ).toList();
    }

}
