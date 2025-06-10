package com.femcoders.events.controllers;

import com.femcoders.events.models.Event;
import com.femcoders.events.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
        //return eventService.getAllEvents();
    }

    @PostMapping("/events")
    public ResponseEntity<Event> addEvent(@RequestBody Event newEvent) {
        Event createdEvent = eventService.addEvent(newEvent);
        return new ResponseEntity<Event>(createdEvent, HttpStatus.CREATED);
    }
}
