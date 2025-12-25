package org.civichelpapi.civichelpapi.report.service.impl;


import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.civichelpapi.civichelpapi.report.service.AuthorityDashboardService;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityDashboardServiceImpl implements AuthorityDashboardService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Report> getMyAssignedReports(
            Long authorityId,
            Pageable pageable
    ) {
        return reportRepository.findByAssignedAuthorityId(authorityId, pageable);
    }

    @Override
    public Page<Report> getUnresolvedReportsInMyCity(
            Long authorityId,
            Pageable pageable
    ) {
        User authority = userRepository.findById(authorityId)
                .orElseThrow(() -> new RuntimeException("Authority not found"));

        Integer cityId = authority.getCity().getId();

        return reportRepository.findByDistrictCityIdAndStatusIn(
                cityId,
                List.of(Status.OPEN, Status.ASSIGNED, Status.IN_PROGRESS),
                pageable
        );
    }
}
