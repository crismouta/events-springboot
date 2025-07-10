package com.femcoders.events.services;

import com.femcoders.events.dtos.event.EventMapper;
import com.femcoders.events.dtos.event.EventRequest;
import com.femcoders.events.dtos.event.EventResponse;
import com.femcoders.events.exception.EntityNotFoundException;
import com.femcoders.events.models.Category;
import com.femcoders.events.models.Event;
import com.femcoders.events.repositories.CategoryRepository;
import com.femcoders.events.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public EventService(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> EventMapper.entityToDto(event)).toList();
    }

    public EventResponse getEventById (Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Event.class.getSimpleName(), id));
        return EventMapper.entityToDto(event);
    }

    public EventResponse addEvent(EventRequest eventRequest){
        //Category foundCategory = categoryRepository.findByName(eventRequest.categoryName()).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        List<Category> categories = eventRequest.categoryNames().stream()
                .map(name -> categoryRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Category not found"))).toList();
        Event newEvent = EventMapper.dtoToEntity(eventRequest, categories);
        Event savedEvent = eventRepository.save(newEvent);
        return EventMapper.entityToDto(savedEvent);
    }
}
