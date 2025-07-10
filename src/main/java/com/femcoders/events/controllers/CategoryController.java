package com.femcoders.events.controllers;

import com.femcoders.events.dtos.category.CategoryRequest;
import com.femcoders.events.dtos.category.CategoryResponse;
import com.femcoders.events.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {

        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory (@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse newECategory = categoryService.addCategory(categoryRequest);
        return new ResponseEntity<>(newECategory, HttpStatus.CREATED);
    }
}
