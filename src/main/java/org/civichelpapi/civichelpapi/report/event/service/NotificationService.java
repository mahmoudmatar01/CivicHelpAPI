package org.civichelpapi.civichelpapi.report.event.service;

import org.civichelpapi.civichelpapi.report.enums.EventType;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(Long userId, Long reportId, EventType eventType) {
        System.out.println("Notify user " + userId + " about report " + reportId + " event: " + eventType.name());

        // Later:
        // sendEmail(userId, reportId, eventType)
        // sendSms(userId, reportId, eventType)
        // createInAppNotification(userId, reportId, eventType)
    }
}

