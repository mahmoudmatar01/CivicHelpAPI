package org.civichelpapi.civichelpapi.ngo.event;

import lombok.Getter;
import org.civichelpapi.civichelpapi.report.entity.Report;
import org.civichelpapi.civichelpapi.report.enums.EventType;
import org.civichelpapi.civichelpapi.user.entity.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class NgoHelpOfferedEvent extends ApplicationEvent {

    private final Report report;
    private final User ngo;

    public NgoHelpOfferedEvent(Object source, Report report, User ngo) {
        super(source);
        this.report = report;
        this.ngo = ngo;
    }

}

