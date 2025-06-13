package com.femcoders.events.services;

import com.femcoders.events.dtos.EventMapper;
import com.femcoders.events.dtos.EventRequest;
import com.femcoders.events.dtos.EventResponse;
import com.femcoders.events.models.Event;
import com.femcoders.events.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> EventMapper.entityToDto(event)).toList();
    }

    public EventResponse addEvent(EventRequest eventRequest){
        Event newEvent = EventMapper.dtoToEntity(eventRequest);
        Event savedEvent = eventRepository.save(newEvent);
        return EventMapper.entityToDto(savedEvent);
    }
}
