package com.femcoders.events.services;

import com.femcoders.events.dtos.category.CategoryMapper;
import com.femcoders.events.dtos.category.CategoryRequest;
import com.femcoders.events.dtos.category.CategoryResponse;
import com.femcoders.events.models.Category;
import com.femcoders.events.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private  final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryMapper.entityToDto(category)).toList();
    }

    public CategoryResponse addCategory(CategoryRequest categoryRequest){
        Category newCategory = CategoryMapper.dtoToEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(newCategory);
        return CategoryMapper.entityToDto(savedCategory);
    }
}
