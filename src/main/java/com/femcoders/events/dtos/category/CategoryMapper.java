package com.femcoders.events.dtos.category;

import com.femcoders.events.models.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static Category dtoToEntity (CategoryRequest dto){
        return new Category(dto.name());
    }

    public static CategoryResponse entityToDto (Category category){
        return new CategoryResponse(category.getName());
    }
}
