package com.eventapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registrations",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "event_id"}))
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status = RegistrationStatus.CONFIRMED;

    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registeredAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "confirmation_code", unique = true)
    private String confirmationCode;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "special_requirements", columnDefinition = "TEXT")
    private String specialRequirements;

    public Registration() {}

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        if (confirmationCode == null) {
            confirmationCode = "EVT-" + System.currentTimeMillis();
        }
        if (status == null) status = RegistrationStatus.CONFIRMED;
        if (paymentStatus == null) paymentStatus = PaymentStatus.PENDING;
    }

    // Getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Event getEvent() { return event; }
    public RegistrationStatus getStatus() { return status; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public String getConfirmationCode() { return confirmationCode; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public String getSpecialRequirements() { return specialRequirements; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setEvent(Event event) { this.event = event; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    public void setCancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }
    public void setConfirmationCode(String confirmationCode) { this.confirmationCode = confirmationCode; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setSpecialRequirements(String specialRequirements) { this.specialRequirements = specialRequirements; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Registration r = new Registration();
        public Builder user(User user) { r.user = user; return this; }
        public Builder event(Event event) { r.event = event; return this; }
        public Builder status(RegistrationStatus status) { r.status = status; return this; }
        public Builder paymentStatus(PaymentStatus paymentStatus) { r.paymentStatus = paymentStatus; return this; }
        public Builder confirmationCode(String code) { r.confirmationCode = code; return this; }
        public Builder specialRequirements(String req) { r.specialRequirements = req; return this; }
        public Registration build() { return r; }
    }

    public enum RegistrationStatus {
        CONFIRMED, CANCELLED, WAITLISTED, ATTENDED
    }

    public enum PaymentStatus {
        PENDING, PAID, REFUNDED, FREE
    }
}
