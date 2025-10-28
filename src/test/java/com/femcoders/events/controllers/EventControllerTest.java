package com.femcoders.events.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.events.dtos.EventRequest;
import com.femcoders.events.models.Event;
import com.femcoders.events.repositories.EventRepository;
import com.femcoders.events.services.EventService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();

        eventRepository.save(new Event("Event 1", "El mejor evento", 30.5));
        eventRepository.save(new Event("Event 2", "El mejor evento", 60.5));
    }

    @Test
    void shouldGetAllEventsSuccessfully() throws Exception {
        mockMvc.perform(get("/events")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Event 1"))
                .andExpect(jsonPath("$[1].name").value("Event 2"))
                .andExpect(jsonPath("$[0].description").value("El mejor evento"))
                .andExpect(jsonPath("$[1].description").value("El mejor evento"))
                .andExpect(jsonPath("$[0].price").value(30.5))
                .andExpect(jsonPath("$[1].price").value(60.5));
    }

    @Test
    void shouldCreateEventSuccessfully() throws Exception {
        EventRequest request = new EventRequest("Event 3", "Un evento nuevo", 80.5);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Event 3"))
                .andExpect(jsonPath("$.description").value("Un evento nuevo"))
                .andExpect(jsonPath("$.price").value(80.5));

        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(3);
    }
}
