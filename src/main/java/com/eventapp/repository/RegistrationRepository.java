package com.eventapp.repository;

import com.eventapp.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserEmail(String email);
    List<Registration> findByEventId(Long eventId);
    Optional<Registration> findByUserEmailAndEventId(String email, Long eventId);
    boolean existsByUserEmailAndEventId(String email, Long eventId);
}