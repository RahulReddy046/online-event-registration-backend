package com.eventapp.dto;

import com.eventapp.model.Registration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RegistrationDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long eventId;
    private String eventTitle;
    private LocalDateTime eventDate;
    private String eventLocation;
    private Registration.RegistrationStatus status;
    private Registration.PaymentStatus paymentStatus;
    private LocalDateTime registeredAt;
    private String confirmationCode;
    private String specialRequirements;
    private BigDecimal registrationFee;

    public RegistrationDTO() {}

    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public Long getEventId() { return eventId; }
    public String getEventTitle() { return eventTitle; }
    public LocalDateTime getEventDate() { return eventDate; }
    public String getEventLocation() { return eventLocation; }
    public Registration.RegistrationStatus getStatus() { return status; }
    public Registration.PaymentStatus getPaymentStatus() { return paymentStatus; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public String getConfirmationCode() { return confirmationCode; }
    public String getSpecialRequirements() { return specialRequirements; }
    public BigDecimal getRegistrationFee() { return registrationFee; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
    public void setStatus(Registration.RegistrationStatus status) { this.status = status; }
    public void setPaymentStatus(Registration.PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    public void setConfirmationCode(String confirmationCode) { this.confirmationCode = confirmationCode; }
    public void setSpecialRequirements(String specialRequirements) { this.specialRequirements = specialRequirements; }
    public void setRegistrationFee(BigDecimal registrationFee) { this.registrationFee = registrationFee; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final RegistrationDTO dto = new RegistrationDTO();
        public Builder id(Long id) { dto.id = id; return this; }
        public Builder userId(Long userId) { dto.userId = userId; return this; }
        public Builder userName(String userName) { dto.userName = userName; return this; }
        public Builder userEmail(String userEmail) { dto.userEmail = userEmail; return this; }
        public Builder eventId(Long eventId) { dto.eventId = eventId; return this; }
        public Builder eventTitle(String eventTitle) { dto.eventTitle = eventTitle; return this; }
        public Builder eventDate(LocalDateTime eventDate) { dto.eventDate = eventDate; return this; }
        public Builder eventLocation(String eventLocation) { dto.eventLocation = eventLocation; return this; }
        public Builder status(Registration.RegistrationStatus status) { dto.status = status; return this; }
        public Builder paymentStatus(Registration.PaymentStatus paymentStatus) { dto.paymentStatus = paymentStatus; return this; }
        public Builder registeredAt(LocalDateTime registeredAt) { dto.registeredAt = registeredAt; return this; }
        public Builder confirmationCode(String confirmationCode) { dto.confirmationCode = confirmationCode; return this; }
        public Builder specialRequirements(String specialRequirements) { dto.specialRequirements = specialRequirements; return this; }
        public Builder registrationFee(BigDecimal registrationFee) { dto.registrationFee = registrationFee; return this; }
        public RegistrationDTO build() { return dto; }
    }
}