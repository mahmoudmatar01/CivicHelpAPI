package org.civichelpapi.civichelpapi.report.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResolutionRequest(
        @NotBlank(message = "Resolution note is required")
        @Size(min = 5, max = 1000, message = "Resolution note must be between 5 and 1000 characters")
        String note
) {
}
