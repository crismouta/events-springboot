package com.femcoders.events.dtos.category;

import com.femcoders.events.models.Category;

public class CategoryMapper {
    public static Category dtoToEntity (CategoryRequest dto){
        return new Category(dto.name());
    }

    public static CategoryResponse entityToDto (Category category){
        return new CategoryResponse(category.getName());
    }
}
