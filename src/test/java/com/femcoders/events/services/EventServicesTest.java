package com.femcoders.events.services;

import com.femcoders.events.dtos.event.EventRequest;
import com.femcoders.events.dtos.event.EventResponse;
import com.femcoders.events.models.Category;
import com.femcoders.events.models.Event;
import com.femcoders.events.repositories.CategoryRepository;
import com.femcoders.events.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServicesTest {
    @Mock
    EventRepository eventRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    EventService eventService;

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Category 1");
        category.setId(1L);
    }

    @Test
    void shouldReturnListOfEventResponses() {
        // Given
        Event event1 = new Event("Event 1", "Description 1", 10.0, category);
        Event event2 = new Event("Event 2", "Description 2", 20.0, category);

        given(eventRepository.findAll()).willReturn(List.of(event1, event2));

        // When
        List<EventResponse> result = eventService.getAllEvents();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Event 1");
        assertThat(result.get(0).categoryName()).isEqualTo("Category 1");
        assertThat(result.get(1).name()).isEqualTo("Event 2");

        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void shouldAddEventWithExistingCategorySuccessfully() {
        // Given
        EventRequest request = new EventRequest("Event 1", "Description 1", 10.0, "Category 1");

        //Event eventToSave = new Event(request.name(), request.description(), request.price(), category);
        Event savedEvent = new Event(request.name(), request.description(), request.price(), category);
        savedEvent.setId(1L);

        given(categoryRepository.findByName("Category 1")).willReturn(Optional.of(category));
        given(eventRepository.save(any(Event.class))).willReturn(savedEvent);

        // When
        EventResponse response = eventService.addEvent(request);

        // Then
        assertThat(response.name()).isEqualTo("Event 1");
        assertThat(response.categoryName()).isEqualTo("Category 1");

        verify(categoryRepository, times(1)).findByName("Category 1");
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryDoesNotExist() {
        // Given
        EventRequest request = new EventRequest("Event 1", "Description 1", 10.0, "NonExistentCategory");

        given(categoryRepository.findByName("NonExistentCategory")).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> eventService.addEvent(request));

        // Then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category not found");

        verify(categoryRepository, times(1)).findByName("NonExistentCategory");
        verify(eventRepository, never()).save(any());
    }
}
