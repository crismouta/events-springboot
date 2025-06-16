package com.femcoders.events.dtos.event;

import com.femcoders.events.models.Category;

public record EventResponse(
        String name,
        String description,
        Double price,
        String categoryName
) {
}
