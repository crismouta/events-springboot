package com.femcoders.events.controllers;

import com.femcoders.events.models.Event;
import com.femcoders.events.services.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/events")
    public void addEvent(@RequestBody Event newEvent) {
        eventService.addEvent(newEvent);
    }
}
