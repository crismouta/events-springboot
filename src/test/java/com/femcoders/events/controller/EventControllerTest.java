package com.femcoders.events.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.events.controllers.EventController;
import com.femcoders.events.dtos.event.EventRequest;
import com.femcoders.events.dtos.event.EventResponse;
import com.femcoders.events.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EventController(eventService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllEvents_returnsList() throws Exception {
        EventResponse event1 = new EventResponse("Event 1", "Description 1", 10.09, "Category1");
        EventResponse event2 = new EventResponse("Event 2", "Description 2", 10.00, "Category2");
        Mockito.when(eventService.getAllEvents()).thenReturn(List.of(event1, event2));

        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Event 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].price").value(10.09))
                .andExpect(jsonPath("$[0].categoryName").value("Category1"))
                .andExpect(jsonPath("$[1].name").value("Event 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].price").value(10.00))
                .andExpect(jsonPath("$[1].categoryName").value("Category2"));
    }

    @Test
    void getAllEvents_returnsEmptyList() throws Exception {
        Mockito.when(eventService.getAllEvents()).thenReturn(List.of());
        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void addEvent_returnsCreatedEvent() throws Exception {
        EventRequest request = new EventRequest("Event 1", "Description 1", 10.0, "Category 1");
        EventResponse response = new EventResponse("Event 1", "Description 1", 10.0, "Category 1");
        Mockito.when(eventService.addEvent(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Event 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.price").value(10.0))
                .andExpect(jsonPath("$.categoryName").value("Category 1"));
    }

    @Test
    void addEvent_invalidRequest_returnsBadRequest() throws Exception {
        EventRequest invalidRequest = new EventRequest("", "", null, null);
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
