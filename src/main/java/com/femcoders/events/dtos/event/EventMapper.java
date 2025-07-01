package com.femcoders.events.dtos.event;

import com.femcoders.events.models.Category;
import com.femcoders.events.models.Event;

import java.util.List;

public class EventMapper {
    public static Event dtoToEntity (EventRequest dto, List<Category> categories){
        return new Event(dto.name(),dto.description(),dto.price(), categories);
    }

    public static EventResponse entityToDto (Event event){
        List<String> categoryNames = event.getCategories().stream()
                .map(name -> name.getName()).toList();
        return new EventResponse(event.getName(),event.getDescription(),event.getPrice(), categoryNames);
    }
}
