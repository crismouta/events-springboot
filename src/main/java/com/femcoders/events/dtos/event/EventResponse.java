package com.femcoders.events.dtos.event;

import java.util.List;

public record EventResponse(
        String name,
        String description,
        Double price,
        List<String> categoryNames
) {
}
