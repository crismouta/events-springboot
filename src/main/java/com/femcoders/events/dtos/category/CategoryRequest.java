package com.femcoders.events.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(@NotBlank(message = "Name is required")
                              @Size(min = 2, max = 50, message = "Name must contain min 2 and max 50 characters")
                              String name) {
}
