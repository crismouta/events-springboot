package com.femcoders.events.services;

import com.femcoders.events.dtos.category.CategoryRequest;
import com.femcoders.events.dtos.category.CategoryResponse;
import com.femcoders.events.models.Category;
import com.femcoders.events.repositories.CategoryRepository;
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
public class CategoryServicesTest {
    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    List<Category> categories;

    @BeforeEach
    void setUp() {
        Category category1 = new Category("Category 1");
        category1.setId(1L);
        category1.setEvents(new ArrayList<>());

        Category category2 = new Category("Category 2");
        category2.setId(2L);
        category2.setEvents(new ArrayList<>());

        this.categories = List.of(category1, category2);
    }

    @Test
    void shouldGetAllCategoriesSuccessfully() {
        // Given
        given(categoryRepository.findAll()).willReturn(this.categories);

        // When
        List<CategoryResponse> result = categoryService.getCategories();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Category 1");
        assertThat(result.get(1).name()).isEqualTo("Category 2");
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        // Given
        CategoryRequest request = new CategoryRequest("New Category");
        Category toSave = new Category(request.name());
        toSave.setEvents(new ArrayList<>());

        Category saved = new Category(request.name());
        saved.setId(1L);
        saved.setEvents(new ArrayList<>());

        given(categoryRepository.save(any(Category.class))).willReturn(saved);

        // When
        CategoryResponse response = categoryService.addCategory(request);

        // Then
        assertThat(response.name()).isEqualTo("New Category");
        assertThat(response.events()).isEmpty();
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}
