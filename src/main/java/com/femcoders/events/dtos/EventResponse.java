package com.femcoders.events.dtos;

public record EventResponse(
        String name,
        String description,
        Double price
) {
}
