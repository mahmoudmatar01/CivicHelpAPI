package org.civichelpapi.civichelpapi.category.dto.response;

import org.civichelpapi.civichelpapi.category.enums.Priority;

public record CategoryResponse(
        Integer id,
        String name,
        long slaHours,
        Priority defaultPriority,
        boolean enabled
) {
}
