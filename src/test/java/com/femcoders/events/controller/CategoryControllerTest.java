package com.femcoders.events.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.events.controllers.CategoryController;
import com.femcoders.events.dtos.category.CategoryRequest;
import com.femcoders.events.dtos.category.CategoryResponse;
import com.femcoders.events.services.CategoryService;
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

@WebMvcTest(CategoryControllerTest.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(categoryService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllCategories_returnsList() throws Exception {
        CategoryResponse category1 = new CategoryResponse("Category 1", List.of());
        CategoryResponse category2 = new CategoryResponse("Category 2", List.of());

        Mockito.when(categoryService.getCategories()).thenReturn(List.of(category1, category2));

        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }

    @Test
    void getAllCategories_noCategoriesFound_returnsEmptyList() throws Exception {
        Mockito.when(categoryService.getCategories()).thenReturn(List.of());

        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void addCategory_returnsCreatedCategory() throws Exception {
        CategoryRequest newCategoryRequest = new CategoryRequest("New Category");
        CategoryResponse newCategoryResponse = new CategoryResponse("New Category", List.of());
        Mockito.when(categoryService.addCategory(Mockito.any())).thenReturn(newCategoryResponse);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void addCategory_withEmptyName_returnsBadRequest() throws Exception {
        CategoryRequest newCategoryRequest = new CategoryRequest("");

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategoryRequest)))
                .andExpect(status().isBadRequest());
    }

}
