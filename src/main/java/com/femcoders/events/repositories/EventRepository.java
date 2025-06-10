package com.femcoders.events.repositories;

import com.femcoders.events.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
