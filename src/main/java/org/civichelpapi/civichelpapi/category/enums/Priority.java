package org.civichelpapi.civichelpapi.category.enums;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    EMERGENCY;

    public Priority escalate() {
        return switch (this) {
            case LOW -> MEDIUM;
            case MEDIUM -> HIGH;
            case HIGH, EMERGENCY -> EMERGENCY;
        };
    }
}
