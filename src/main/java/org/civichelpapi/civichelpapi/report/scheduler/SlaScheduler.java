package org.civichelpapi.civichelpapi.report.scheduler;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.category.enums.Priority;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlaScheduler {

    private final ReportRepository reportRepository;

    @Scheduled(fixedRate = 60 * 60 * 1000) // every hour
    public void checkSla() {

        LocalDateTime now = LocalDateTime.now();

        List<Report> overdueReports = reportRepository.findAll()
                .stream()
                .filter(r -> r.getStatus() != Status.RESOLVED && r.getStatus() != Status.CLOSED)
                .filter(r -> r.getCreatedAt().plusHours(r.getCategory().getSlaHours()).isBefore(now))
                .toList();

        for (Report report : overdueReports) {
            if (report.getPriority() != Priority.EMERGENCY) {
                report.setPriority(Priority.EMERGENCY);
                // TODO: send notification to citizens
            }
            reportRepository.save(report);
        }
    }
}
