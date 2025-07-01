package com.femcoders.events.dtos.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EventRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must contain min 2 and max 50 characters")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        Double price,
        List<String> categoryNames
) {
}
