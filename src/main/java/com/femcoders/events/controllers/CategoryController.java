package com.femcoders.events.controllers;

import com.femcoders.events.dtos.category.CategoryRequest;
import com.femcoders.events.dtos.category.CategoryResponse;
import com.femcoders.events.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> addCategory (@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse newECategory = categoryService.addCategory(categoryRequest);
        return new ResponseEntity<>(newECategory, HttpStatus.CREATED);
    }
}
