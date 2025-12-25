package org.civichelpapi.civichelpapi.category.dto.request;

import org.civichelpapi.civichelpapi.category.enums.Priority;

public record CategoryRequest(
        String name,
        long slaHours,
        Priority defaultPriority
) {
}
