package com.femcoders.events.controllers;

import com.femcoders.events.dtos.event.EventRequest;
import com.femcoders.events.dtos.event.EventResponse;
import com.femcoders.events.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
       return new ResponseEntity<>(eventService.getAllEvents(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        /*try {
            return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }*/

        return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventResponse> addEvent(@Valid @RequestBody EventRequest eventRequest) {
        return new ResponseEntity<>(eventService.addEvent(eventRequest), HttpStatus.CREATED);
    }
}
