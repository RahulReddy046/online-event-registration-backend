package com.eventapp.service;

import com.eventapp.dto.EventDTO;
import com.eventapp.exception.EventNotFoundException;
import com.eventapp.model.Event;
import com.eventapp.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Page<EventDTO> getAllEvents(Pageable pageable, String category, String status) {
        Page<Event> events;
        if (category != null && !category.isEmpty()) {
            events = eventRepository.findByCategory(Event.Category.valueOf(category), pageable);
        } else if (status != null && !status.isEmpty()) {
            events = eventRepository.findByStatus(Event.EventStatus.valueOf(status), pageable);
        } else {
            events = eventRepository.findAll(pageable);
        }
        return events.map(this::toDTO);
    }

    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return toDTO(event);
    }

    public Page<EventDTO> searchEvents(String keyword, Pageable pageable) {
        return eventRepository.findByTitleContainingIgnoreCase(keyword, pageable).map(this::toDTO);
    }

    public EventDTO createEvent(EventDTO dto) {
        Event event = toEntity(dto);
        return toDTO(eventRepository.save(event));
    }

    public EventDTO updateEvent(Long id, EventDTO dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setEndDate(dto.getEndDate());
        event.setLocation(dto.getLocation());
        event.setMaxCapacity(dto.getMaxCapacity());
        event.setRegistrationFee(dto.getRegistrationFee());
        event.setCategory(dto.getCategory());
        event.setStatus(dto.getStatus());
        event.setOrganizerName(dto.getOrganizerName());
        event.setOrganizerEmail(dto.getOrganizerEmail());
        event.setImageUrl(dto.getImageUrl());
        return toDTO(eventRepository.save(event));
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
    }

    public EventDTO toDTO(Event e) {
        EventDTO dto = new EventDTO();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setDescription(e.getDescription());
        dto.setEventDate(e.getEventDate());
        dto.setEndDate(e.getEndDate());
        dto.setLocation(e.getLocation());
        dto.setMaxCapacity(e.getMaxCapacity());
        dto.setCurrentRegistrations(e.getCurrentRegistrations());
        dto.setRegistrationFee(e.getRegistrationFee());
        dto.setCategory(e.getCategory());
        dto.setStatus(e.getStatus());
        dto.setOrganizerName(e.getOrganizerName());
        dto.setOrganizerEmail(e.getOrganizerEmail());
        dto.setImageUrl(e.getImageUrl());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }

    public Event toEntity(EventDTO dto) {
        Event e = new Event();
        e.setTitle(dto.getTitle());
        e.setDescription(dto.getDescription());
        e.setEventDate(dto.getEventDate());
        e.setEndDate(dto.getEndDate());
        e.setLocation(dto.getLocation());
        e.setMaxCapacity(dto.getMaxCapacity());
        e.setRegistrationFee(dto.getRegistrationFee());
        e.setCategory(dto.getCategory());
        e.setStatus(dto.getStatus() != null ? dto.getStatus() : Event.EventStatus.UPCOMING);
        e.setOrganizerName(dto.getOrganizerName());
        e.setOrganizerEmail(dto.getOrganizerEmail());
        e.setImageUrl(dto.getImageUrl());
        return e;
    }
}