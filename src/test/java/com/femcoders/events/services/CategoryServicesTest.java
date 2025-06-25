package com.femcoders.events.services;

import com.femcoders.events.dtos.category.CategoryRequest;
import com.femcoders.events.dtos.category.CategoryResponse;
import com.femcoders.events.models.Category;
import com.femcoders.events.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryServicesTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCategories_returnsListOfCategoryResponse() {
        Category category1 = new Category("Category 1");
        category1.setId(1L);
        category1.setEvents(List.of());
        Category category2 = new Category("Category 2");
        category2.setId(2L);
        category2.setEvents(List.of());
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<CategoryResponse> result = categoryService.getCategories();
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).name());
        assertEquals("Category 2", result.get(1).name());
    }

    @Test
    void addCategory_savesAndReturnsCategoryResponse() {
        CategoryRequest request = new CategoryRequest("New Category");
        Category categoryToSave = new Category("New Category");
        categoryToSave.setEvents(List.of());
        Category savedCategory = new Category("New Category");
        savedCategory.setId(1L);
        savedCategory.setEvents(List.of());
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(savedCategory);

        CategoryResponse response = categoryService.addCategory(request);
        assertEquals("New Category", response.name());
    }
}
