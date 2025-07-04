package com.femcoders.events.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.events.dtos.category.CategoryRequest;
import com.femcoders.events.dtos.category.CategoryResponse;
import com.femcoders.events.services.CategoryService;
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
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<CategoryResponse> categoryResponses;

    @BeforeEach
    void setUp() {
        categoryResponses = new ArrayList<>();

        CategoryResponse category1 = new CategoryResponse("Category 1", List.of("Event 1", "Event 2"));
        CategoryResponse category2 = new CategoryResponse("Category 2", List.of("Event 3", "Event 4"));

        categoryResponses.add(category1);
        categoryResponses.add(category2);
    }

    @Test
    void shouldGetAllCategoriesSuccessfully() throws Exception {
        // Given
        given(categoryService.getCategories()).willReturn(categoryResponses);

        // When & Then
        mockMvc.perform(get("/categories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Category 2"))
                .andExpect(jsonPath("$[0].events[0]").value("Event 1"))
                .andExpect(jsonPath("$[0].events[1]").value("Event 2"));
    }

    @Test
    void shouldCreateCategorySuccessfully() throws Exception {
        // Given
        CategoryRequest request = new CategoryRequest("Category 3");
        CategoryResponse savedResponse = new CategoryResponse("Category 3", List.of());

        given(categoryService.addCategory(Mockito.any(CategoryRequest.class))).willReturn(savedResponse);

        String json = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Category 3"))
                .andExpect(jsonPath("$.events").isEmpty());
    }

    @Test
    void shouldReturnBadRequestWhenCategoryNameIsInvalid() throws Exception {
        // Given
        CategoryRequest invalidRequest = new CategoryRequest("");
        String json = objectMapper.writeValueAsString(invalidRequest);

        // When & Then
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
