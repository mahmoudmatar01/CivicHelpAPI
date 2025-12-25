package org.civichelpapi.civichelpapi.report.service.impl;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.exception.BusinessException;
import org.civichelpapi.civichelpapi.exception.NotFoundException;
import org.civichelpapi.civichelpapi.report.dto.response.ReportResponse;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.civichelpapi.civichelpapi.report.service.AuthorityReportService;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.civichelpapi.civichelpapi.report.helper.ReportHelper.toReportResponse;

@Service
@RequiredArgsConstructor
public class AuthorityReportServiceImpl implements AuthorityReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Override
    public ReportResponse assignReport(Long reportId, Long authorityId) {

        Report report = getReport(reportId);
        User authority = getAuthority(authorityId);

        if (!report.getDistrict().getCity().getId().equals(authority.getCity().getId())) {
            throw new BusinessException("Report outside authority jurisdiction");
        }

        validateTransition(report.getStatus(), Status.ASSIGNED);

        report.setAssignedAuthority(authority);
        report.setStatus(Status.ASSIGNED);

        return toReportResponse(reportRepository.save(report));
    }

    @Override
    public ReportResponse startProgress(Long reportId, Long authorityId) {

        Report report = getReport(reportId);

        if (!report.getAssignedAuthority().getId().equals(authorityId)) {
            throw new BusinessException("Not assigned to this authority");
        }

        validateTransition(report.getStatus(), Status.IN_PROGRESS);

        report.setStatus(Status.IN_PROGRESS);
        return toReportResponse(reportRepository.save(report));
    }

    @Override
    public ReportResponse resolveReport(Long reportId, Long authorityId, String note) {

        Report report = getReport(reportId);

        if (!report.getAssignedAuthority().getId().equals(authorityId)) {
            throw new BusinessException("Not assigned to this authority");
        }

        if (note == null || note.isBlank()) {
            throw new BusinessException("Resolution note is required");
        }

        validateTransition(report.getStatus(), Status.RESOLVED);

        report.setStatus(Status.RESOLVED);
        report.setResolutionNote(note);
        report.setResolvedAt(LocalDateTime.now());

        return toReportResponse(reportRepository.save(report));
    }

    private Report getReport(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));
    }

    private User getAuthority(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Authority not found"));
    }

    private void validateTransition(Status current, Status next) {

        if (current == next) {
            throw new BusinessException("Report is already in status: " + current);
        }

        switch (current) {

            case OPEN -> {
                if (next != Status.ASSIGNED && next != Status.REJECTED) {
                    throw new BusinessException("OPEN reports can only be ASSIGNED or REJECTED");
                }
            }

            case ASSIGNED -> {
                if (next != Status.IN_PROGRESS) {
                    throw new BusinessException("ASSIGNED reports can only move to IN_PROGRESS");
                }
            }

            case IN_PROGRESS -> {
                if (next != Status.RESOLVED) {
                    throw new BusinessException("IN_PROGRESS reports can only be RESOLVED");
                }
            }

            case RESOLVED -> {
                if (next != Status.CLOSED) {
                    throw new BusinessException("RESOLVED reports can only be CLOSED by citizen");
                }
            }

            case CLOSED, REJECTED -> {
                throw new BusinessException("Closed or rejected reports cannot change status");
            }

            default -> throw new BusinessException("Invalid report status");
        }
    }

}
