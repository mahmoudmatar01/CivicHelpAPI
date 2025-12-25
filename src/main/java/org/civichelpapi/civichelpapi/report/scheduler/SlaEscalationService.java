package org.civichelpapi.civichelpapi.report.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlaEscalationService {

    private final ReportRepository reportRepository;

    @Transactional
    public void processSlaBreaches() {

        List<Report> reports =
                reportRepository.findReportsBreachingSla(
                        List.of(
                                Status.OPEN,
                                Status.ASSIGNED,
                                Status.IN_PROGRESS
                        ),
                        LocalDateTime.now()
                );

        for (Report report : reports) {
            report.setSlaBreached(true);
            report.setPriority(report.getPriority().escalate());
        }

        reportRepository.saveAll(reports);
    }
}
