package com.femcoders.events.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Long id) {
        super (entityName + " not found with id " + id);
    }
}
