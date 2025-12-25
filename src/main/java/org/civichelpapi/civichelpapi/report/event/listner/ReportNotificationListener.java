package org.civichelpapi.civichelpapi.report.event.listner;

import lombok.RequiredArgsConstructor;
import org.civichelpapi.civichelpapi.report.event.ReportEvent;
import org.civichelpapi.civichelpapi.report.event.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportNotificationListener {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void handleReportEvent(ReportEvent event) {
        notificationService.sendNotification(
                event.getRecipientId(),
                event.getReportId(),
                event.getEventType()
        );
    }
}
