package com.femcoders.events.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.events.dtos.event.EventRequest;
import com.femcoders.events.dtos.event.EventResponse;
import com.femcoders.events.services.EventService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<EventResponse> eventResponses;

    @BeforeEach
    void setUp() {
        eventResponses = new ArrayList<>();

        EventResponse event1 = new EventResponse("Event 1", "El mejor evento", 30.5, "Category 1");
        EventResponse event2 = new EventResponse("Event 2", "El mejor evento", 60.5, "Category 1");

        eventResponses.add(event1);
        eventResponses.add(event2);
    }

    @Test
    void shouldGetAllEventsSuccessfully() throws Exception {
        // Given
        given(eventService.getAllEvents()).willReturn(eventResponses);

        // When & Then
        mockMvc.perform(get("/events").accept(MediaType.APPLICATION_JSON))
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
        // Given
        EventRequest request = new EventRequest("Event 3", "El mejor evento", 80.5, "Category 1");
        EventResponse savedResponse = new EventResponse("Event 3", "El mejor evento", 80.5, "Category 1");

        given(eventService.addEvent(Mockito.any(EventRequest.class))).willReturn(savedResponse);

        String json = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Event 3"))
                .andExpect(jsonPath("$.description").value("El mejor evento"))
                .andExpect(jsonPath("$.price").value(80.5));
    }

    @Test
    void shouldReturnBadRequestWhenEventNameIsInvalid() throws Exception {
        // Given
        EventRequest invalidRequest = new EventRequest("", "", 0.0, "");
        String json = objectMapper.writeValueAsString(invalidRequest);

        // When & Then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
