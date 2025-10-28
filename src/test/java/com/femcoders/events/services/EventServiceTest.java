package com.femcoders.events.services;

import com.femcoders.events.dtos.EventRequest;
import com.femcoders.events.dtos.EventResponse;
import com.femcoders.events.models.Event;
import com.femcoders.events.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    EventRepository eventRepository;

    @InjectMocks
    EventService eventService;

    List<Event> events;

    @BeforeEach
    void setUp() {
        events = new ArrayList<>();
        Event event1 = new Event("Event 1", "Description 1", 10.0);
        Event event2 = new Event("Event 2", "Description 2", 20.0);
        events.add(event1);
        events.add(event2);
    }

    @Test
    void shouldReturnEventsSuccessfully() {

        given(eventRepository.findAll()).willReturn(events);

        // When
        List<EventResponse> result = eventService.getAllEvents();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Event 1");
        assertThat(result.get(1).name()).isEqualTo("Event 2");

        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateEventSuccessfully() {
        // Given
        EventRequest request = new EventRequest("Event 3", "Description 3", 10.0);

        Event savedEvent = new Event(request.name(), request.description(), request.price());
        savedEvent.setId(1L);

        given(eventRepository.save(any(Event.class))).willReturn(savedEvent);

        // When
        EventResponse response = eventService.addEvent(request);

        // Then
        assertThat(response.name()).isEqualTo("Event 3");
        verify(eventRepository, times(1)).save(any(Event.class));
    }
}
