package org.civichelpapi.civichelpapi.ngo.event.listner;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.ngo.event.NgoHelpOfferedEvent;
import org.civichelpapi.civichelpapi.ngo.event.service.NgoHelpNotificationService;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NgoHelpNotificationListener {

    private final NgoHelpNotificationService ngoHelpNotificationService;

    @Async
    @EventListener
    public void handleReportEvent(NgoHelpOfferedEvent event) {

        Report report = event.getReport();
        User ngo = event.getNgo();

        ngoHelpNotificationService.notifyAdmin(ngo,report);

        if (report.getAssignedAuthority() != null) {
            ngoHelpNotificationService.notifyAuthority(report,ngo);
        }

    }
}
