package org.civichelpapi.civichelpapi.report.event;

import lombok.Getter;
import org.civichelpapi.civichelpapi.report.enums.EventType;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReportEvent extends ApplicationEvent {

    private final Long reportId;
    private final EventType eventType;
    private final Long recipientId;

    public ReportEvent(Object source, Long reportId, EventType eventType, Long recipientId) {
        super(source);
        this.reportId = reportId;
        this.eventType = eventType;
        this.recipientId = recipientId;
    }

}

