package com.femcoders.events.dtos.category;

import com.femcoders.events.models.Event;

import java.util.List;

public record CategoryResponse(String name, List<String> events) {
}
