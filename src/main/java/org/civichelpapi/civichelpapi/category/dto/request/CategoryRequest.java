package org.civichelpapi.civichelpapi.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.civichelpapi.civichelpapi.category.enums.Priority;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
        String name,

        @Positive(message = "SLA hours must be a positive number")
        long slaHours,

        @NotNull(message = "Default priority is required")
        Priority defaultPriority
) {
}
