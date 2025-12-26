package org.civichelpapi.civichelpapi.ngo.event.service;

import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.EventType;
import org.civichelpapi.civichelpapi.report.event.service.NotificationService;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class NgoHelpNotificationService {

    public void notifyAdmin(User ngo, Report report) {
        System.out.println( "NGO " + ngo.getFullName() + " offered help on report #" + report.getId());
    }

    public void notifyAuthority(Report report,User ngo) {
        System.out.println( "NGO Authority");
        System.out.println( "Hello " + report.getAssignedAuthority() +
                ", NGO " + ngo.getFullName() + " offered help on report #" + report.getId());
    }
}

