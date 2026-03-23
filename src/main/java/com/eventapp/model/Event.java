package com.eventapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotBlank
    @Column(nullable = false)
    private String location;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "current_registrations")
    private Integer currentRegistrations = 0;

    @Column(name = "registration_fee", precision = 10, scale = 2)
    private BigDecimal registrationFee = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.UPCOMING;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "organizer_name")
    private String organizerName;

    @Column(name = "organizer_email")
    private String organizerEmail;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations;

    public Event() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (currentRegistrations == null) currentRegistrations = 0;
        if (registrationFee == null) registrationFee = BigDecimal.ZERO;
        if (status == null) status = EventStatus.UPCOMING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getEventDate() { return eventDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public String getLocation() { return location; }
    public Integer getMaxCapacity() { return maxCapacity; }
    public Integer getCurrentRegistrations() { return currentRegistrations == null ? 0 : currentRegistrations; }
    public BigDecimal getRegistrationFee() { return registrationFee; }
    public Category getCategory() { return category; }
    public EventStatus getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }
    public String getOrganizerName() { return organizerName; }
    public String getOrganizerEmail() { return organizerEmail; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public User getCreatedBy() { return createdBy; }
    public List<Registration> getRegistrations() { return registrations; }

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
    public void setCategory(Category category) { this.category = category; }
    public void setStatus(EventStatus status) { this.status = status; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public void setOrganizerEmail(String organizerEmail) { this.organizerEmail = organizerEmail; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }

    // Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private final Event event = new Event();
        public Builder title(String t) { event.title = t; return this; }
        public Builder description(String d) { event.description = d; return this; }
        public Builder eventDate(LocalDateTime d) { event.eventDate = d; return this; }
        public Builder endDate(LocalDateTime d) { event.endDate = d; return this; }
        public Builder location(String l) { event.location = l; return this; }
        public Builder maxCapacity(Integer m) { event.maxCapacity = m; return this; }
        public Builder currentRegistrations(Integer c) { event.currentRegistrations = c; return this; }
        public Builder registrationFee(BigDecimal f) { event.registrationFee = f; return this; }
        public Builder category(Category c) { event.category = c; return this; }
        public Builder status(EventStatus s) { event.status = s; return this; }
        public Builder imageUrl(String u) { event.imageUrl = u; return this; }
        public Builder organizerName(String n) { event.organizerName = n; return this; }
        public Builder organizerEmail(String e) { event.organizerEmail = e; return this; }
        public Builder createdBy(User u) { event.createdBy = u; return this; }
        public Event build() { return event; }
    }

    public enum Category {
        CONFERENCE, WORKSHOP, SEMINAR, CONCERT, SPORTS,
        NETWORKING, WEBINAR, FESTIVAL, OTHER
    }

    public enum EventStatus {
        UPCOMING, ONGOING, COMPLETED, CANCELLED
    }
}