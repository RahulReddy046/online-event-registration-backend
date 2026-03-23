package com.eventapp.repository;

import com.eventapp.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Event> findByCategory(Event.Category category, Pageable pageable);
    Page<Event> findByStatus(Event.EventStatus status, Pageable pageable);
}