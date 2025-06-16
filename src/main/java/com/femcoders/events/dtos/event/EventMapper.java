package com.femcoders.events.dtos.event;

import com.femcoders.events.models.Category;
import com.femcoders.events.models.Event;

public class EventMapper {
    public static Event dtoToEntity (EventRequest dto, Category category){
        return new Event(dto.name(),dto.description(),dto.price(), category);
    }

    public static EventResponse entityToDto (Event event){
        String category = (event.getCategory() != null) ? event.getCategory().getName() : null;
        return new EventResponse(event.getName(),event.getDescription(),event.getPrice(), category);
    }
}
