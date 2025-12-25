package org.civichelpapi.civichelpapi.report.service.impl;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.category.entity.Category;
import org.civichelpapi.civichelpapi.category.repository.CategoryRepository;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.civichelpapi.civichelpapi.location.entity.District;
import org.civichelpapi.civichelpapi.location.reposirory.DistrictRepository;
import org.civichelpapi.civichelpapi.report.dto.request.ReportRequest;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.helper.ReportHelper;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.civichelpapi.civichelpapi.report.service.CitizenReportService;
import org.civichelpapi.civichelpapi.report.service.ReportService;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.civichelpapi.civichelpapi.report.helper.ReportHelper.toReportResponse;


@Service
@RequiredArgsConstructor
public class CitizenReportServiceImpl implements CitizenReportService, ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DistrictRepository districtRepository;

    @Override
    public ReportResponse createReport(Long citizenId, ReportRequest request) {
        User citizen = userRepository.findById(citizenId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        if (!category.isEnabled()) {
            throw new BusinessException("Category is disabled");
        }

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new NotFoundException("District not found"));

        Report report = new Report();
        report.setCitizen(citizen);
        report.setCategory(category);
        report.setDistrict(district);
        report.setDescription(request.description());
        report.setImageUrls(request.imagesUrl());
        report.setStatus(Status.OPEN);
        report.setPriority(category.getDefaultPriority());

        return toReportResponse(reportRepository.save(report));

    }

    @Override
    public List<ReportResponse> getCitizenReports(Long citizenId) {
        return reportRepository.findByCitizenId(citizenId)
                .stream()
                .map(ReportHelper::toReportResponse)
                .toList();
    }

//    @Override
//    public void updateReportStatus(Long reportId, Status newStatus) {
//
//        Report report = reportRepository.findById(reportId).orElseThrow(() -> new NotFoundException("Report not found"));
//
//        Status current = report.getStatus();
//
//        switch (current) {
//            case OPEN -> {
//                if (newStatus != Status.ASSIGNED && newStatus != Status.REJECTED)
//                    throw new BusinessException("Invalid status transition");
//            }
//            case ASSIGNED -> {
//                if (newStatus != Status.IN_PROGRESS)
//                    throw new BusinessException("Invalid status transition");
//            }
//            case IN_PROGRESS -> {
//                if (newStatus != Status.RESOLVED)
//                    throw new BusinessException("Invalid status transition");
//            }
//            case RESOLVED -> {
//                if (newStatus != Status.CLOSED)
//                    throw new BusinessException("Only citizen can close this report");
//            }
//            default -> throw new BusinessException("Invalid current status");
//        }
//
//        report.setStatus(newStatus);
//        reportRepository.save(report);
//    }

    @Override
    public ReportResponse closeReport(Long reportId, Long citizenId) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("Report not found"));

        if (!report.getCitizen().getId().equals(citizenId)) {
            throw new BusinessException("You can only close your own reports");
        }

        if (report.getStatus() != Status.RESOLVED) {
            throw new BusinessException("Only resolved reports can be closed");
        }

        report.setStatus(Status.CLOSED);
        return toReportResponse(reportRepository.save(report));
    }
}
