package com.femcoders.events.services;

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

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event addEvent(Event newEvent){
        return eventRepository.save(newEvent);
    }
}
