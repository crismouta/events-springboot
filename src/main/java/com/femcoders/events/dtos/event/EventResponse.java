package com.femcoders.events.dtos.event;

public record EventResponse(
        String name,
        String description,
        Double price,
        String categoryName
) {
}
