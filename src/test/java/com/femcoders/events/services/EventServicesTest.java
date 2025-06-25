package com.femcoders.events.services;

import com.femcoders.events.dtos.event.EventRequest;
import com.femcoders.events.dtos.event.EventResponse;
import com.femcoders.events.models.Category;
import com.femcoders.events.models.Event;
import com.femcoders.events.repositories.CategoryRepository;
import com.femcoders.events.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EventServicesTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEvents_returnsListOfEventResponse() {
        Category category = new Category("Category 1");
        Event event1 = new Event("Event 1", "Description 1", 10.0, category);
        Event event2 = new Event("Event 2", "Description 2", 20.0, category);
        Mockito.when(eventRepository.findAll()).thenReturn(List.of(event1, event2));

        List<EventResponse> result = eventService.getAllEvents();
        assertEquals(2, result.size());
        assertEquals("Event 1", result.get(0).name());
        assertEquals("Event 2", result.get(1).name());
        assertEquals("Category 1", result.get(0).categoryName());
    }

    @Test
    void addEvent_withExistingCategory_returnsEventResponse() {
        Category category = new Category("Category 1");
        EventRequest request = new EventRequest("Event 1", "Description 1", 10.0, "Category 1");
        Event eventToSave = new Event("Event 1", "Description 1", 10.0, category);
        Event savedEvent = new Event("Event 1", "Description 1", 10.0, category);
        Mockito.when(categoryRepository.findByName("Category 1")).thenReturn(Optional.of(category));
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(savedEvent);

        EventResponse response = eventService.addEvent(request);
        assertEquals("Event 1", response.name());
        assertEquals("Category 1", response.categoryName());
    }

    @Test
    void addEvent_withNonExistingCategory_throwsException() {
        EventRequest request = new EventRequest("Event 1", "Description 1", 10.0, "NonExistentCategory");
        Mockito.when(categoryRepository.findByName("NonExistentCategory")).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> eventService.addEvent(request));
        assertEquals("Category not found", exception.getMessage());
    }
}
