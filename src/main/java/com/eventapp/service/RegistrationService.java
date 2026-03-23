package com.eventapp.service;

import com.eventapp.dto.RegistrationDTO;
import com.eventapp.exception.RegistrationException;
import com.eventapp.model.Event;
import com.eventapp.model.Registration;
import com.eventapp.model.User;
import com.eventapp.repository.EventRepository;
import com.eventapp.repository.RegistrationRepository;
import com.eventapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               EventRepository eventRepository,
                               UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RegistrationDTO registerForEvent(Long eventId, String email, String utrNumber) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RegistrationException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RegistrationException("Event not found"));

        if (registrationRepository.existsByUserEmailAndEventId(email, eventId)) {
            throw new RegistrationException("You are already registered for this event");
        }

        if (event.getMaxCapacity() != null &&
                (event.getCurrentRegistrations() >= event.getMaxCapacity())) {
            throw new RegistrationException("Event is full");
        }

        // Determine payment status
        Registration.PaymentStatus paymentStatus;
        if (utrNumber != null && !utrNumber.isEmpty()) {
            paymentStatus = Registration.PaymentStatus.PENDING;
        } else if (event.getRegistrationFee() == null ||
                event.getRegistrationFee().compareTo(java.math.BigDecimal.ZERO) == 0) {
            paymentStatus = Registration.PaymentStatus.FREE;
        } else {
            paymentStatus = Registration.PaymentStatus.PENDING;
        }

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);
        registration.setStatus(Registration.RegistrationStatus.CONFIRMED);
        registration.setPaymentStatus(paymentStatus);
        registration.setSpecialRequirements(utrNumber != null ? "UTR: " + utrNumber : null);

        Registration saved = registrationRepository.save(registration);

        // Update registration count
        event.setCurrentRegistrations((event.getCurrentRegistrations() == null ? 0
                : event.getCurrentRegistrations()) + 1);
        eventRepository.save(event);

        return toDTO(saved);
    }

    @Transactional
    public void cancelRegistration(Long registrationId, String email) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RegistrationException("Registration not found"));

        if (!registration.getUser().getEmail().equals(email)) {
            throw new RegistrationException("You can only cancel your own registrations");
        }

        registration.setStatus(Registration.RegistrationStatus.CANCELLED);
        registration.setCancelledAt(LocalDateTime.now());
        registrationRepository.save(registration);

        Event event = registration.getEvent();
        event.setCurrentRegistrations(Math.max(0,
                (event.getCurrentRegistrations() == null ? 0 : event.getCurrentRegistrations()) - 1));
        eventRepository.save(event);
    }

    public List<RegistrationDTO> getUserRegistrations(String email) {
        return registrationRepository.findByUserEmail(email)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RegistrationDTO> getEventRegistrations(Long eventId) {
        return registrationRepository.findByEventId(eventId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RegistrationDTO toDTO(Registration r) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(r.getId());
        dto.setUserId(r.getUser().getId());
        dto.setUserName(r.getUser().getName());
        dto.setUserEmail(r.getUser().getEmail());
        dto.setEventId(r.getEvent().getId());
        dto.setEventTitle(r.getEvent().getTitle());
        dto.setEventDate(r.getEvent().getEventDate());
        dto.setEventLocation(r.getEvent().getLocation());
        dto.setStatus(r.getStatus());
        dto.setPaymentStatus(r.getPaymentStatus());
        dto.setRegisteredAt(r.getRegisteredAt());
        dto.setConfirmationCode(r.getConfirmationCode());
        dto.setSpecialRequirements(r.getSpecialRequirements());
        dto.setRegistrationFee(r.getEvent().getRegistrationFee());
        return dto;
    }
}