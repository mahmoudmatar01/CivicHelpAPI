package org.civichelpapi.civichelpapi.report.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlaEscalationJob {

    private final SlaEscalationService escalationService;

    @Scheduled(fixedRate = 60 * 60 * 1000) // every hour
    public void run() {
        escalationService.processSlaBreaches();
    }
}
