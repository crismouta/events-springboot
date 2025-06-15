package com.femcoders.events.dtos.event;

import com.femcoders.events.models.Event;

public class EventMapper {
    public static Event dtoToEntity (EventRequest dto){
        return new Event(dto.name(),dto.description(),dto.price());
    }

    public static EventResponse entityToDto (Event event){
        return new EventResponse(event.getName(),event.getDescription(),event.getPrice());
    }
}
