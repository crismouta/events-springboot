package com.femcoders.events.dtos.category;

import com.femcoders.events.models.Category;
import com.femcoders.events.models.Event;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static Category dtoToEntity (CategoryRequest dto){
        return new Category(dto.name());
    }

    public static CategoryResponse entityToDto (Category category){
        List<String> events = category.getEvents().stream()
                .map(event -> event.getName())
                .toList();
        /*List<String> events = new ArrayList<>();
        for (Event event : category.getEvents()) {
            events.add(event.getName());
        }*/
        return new CategoryResponse(category.getName(), events);
    }
}
