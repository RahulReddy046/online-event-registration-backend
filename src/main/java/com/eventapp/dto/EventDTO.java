package com.eventapp.dto;

import com.eventapp.model.Event;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private LocalDateTime endDate;
    private String location;
    private Integer maxCapacity;
    private Integer currentRegistrations;
    private BigDecimal registrationFee;
    private Event.Category category;
    private Event.EventStatus status;
    private String imageUrl;
    private String organizerName;
    private String organizerEmail;
    private LocalDateTime createdAt;
    private String createdByName;
    private String createdByEmail;

    public EventDTO() {}

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getEventDate() { return eventDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getLocation() { return location; }
    public Integer getMaxCapacity() { return maxCapacity; }
    public Integer getCurrentRegistrations() { return currentRegistrations; }
    public BigDecimal getRegistrationFee() { return registrationFee; }
    public Event.Category getCategory() { return category; }
    public Event.EventStatus getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }
    public String getOrganizerName() { return organizerName; }
    public String getOrganizerEmail() { return organizerEmail; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getCreatedByName() { return createdByName; }
    public String getCreatedByEmail() { return createdByEmail; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public void setLocation(String location) { this.location = location; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setCurrentRegistrations(Integer c) { this.currentRegistrations = c; }
    public void setRegistrationFee(BigDecimal registrationFee) { this.registrationFee = registrationFee; }
    public void setCategory(Event.Category category) { this.category = category; }
    public void setStatus(Event.EventStatus status) { this.status = status; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public void setOrganizerEmail(String organizerEmail) { this.organizerEmail = organizerEmail; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }
    public void setCreatedByEmail(String createdByEmail) { this.createdByEmail = createdByEmail; }

    // Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final EventDTO dto = new EventDTO();
        public Builder id(Long id) { dto.id = id; return this; }
        public Builder title(String t) { dto.title = t; return this; }
        public Builder description(String d) { dto.description = d; return this; }
        public Builder eventDate(LocalDateTime d) { dto.eventDate = d; return this; }
        public Builder endDate(LocalDateTime d) { dto.endDate = d; return this; }
        public Builder location(String l) { dto.location = l; return this; }
        public Builder maxCapacity(Integer m) { dto.maxCapacity = m; return this; }
        public Builder currentRegistrations(Integer c) { dto.currentRegistrations = c; return this; }
        public Builder registrationFee(BigDecimal f) { dto.registrationFee = f; return this; }
        public Builder category(Event.Category c) { dto.category = c; return this; }
        public Builder status(Event.EventStatus s) { dto.status = s; return this; }
        public Builder organizerName(String n) { dto.organizerName = n; return this; }
        public Builder organizerEmail(String e) { dto.organizerEmail = e; return this; }
        public Builder createdAt(LocalDateTime d) { dto.createdAt = d; return this; }
        public Builder createdByName(String n) { dto.createdByName = n; return this; }
        public Builder createdByEmail(String e) { dto.createdByEmail = e; return this; }
        public EventDTO build() { return dto; }
    }
}