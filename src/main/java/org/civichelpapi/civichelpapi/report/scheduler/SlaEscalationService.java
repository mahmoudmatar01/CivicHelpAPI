package org.civichelpapi.civichelpapi.report.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.EventType;
import org.civichelpapi.civichelpapi.report.enums.Status;
import org.civichelpapi.civichelpapi.report.event.ReportEvent;
import org.civichelpapi.civichelpapi.report.repository.ReportRepository;
import org.civichelpapi.civichelpapi.user.enums.Role;
import org.civichelpapi.civichelpapi.user.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlaEscalationService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

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

            reportRepository.save(report);

            Long authorityId = userRepository.findByRoleAndCityId(Role.AUTHORITY, report.getDistrict().getCity().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "No authority assigned for this city"
                    )).getId();

            eventPublisher.publishEvent(new ReportEvent(
                    this,
                    report.getId(),
                    EventType.ESCALATED,
                    authorityId
            ));

        }

    }
}
