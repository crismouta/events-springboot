package com.femcoders.events.dtos.category;

import java.util.List;

public record CategoryResponse(String name, List<String> events) {
}
