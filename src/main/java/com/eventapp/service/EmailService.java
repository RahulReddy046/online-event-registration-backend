package com.eventapp.service;

import com.eventapp.model.Event;
import com.eventapp.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a");

    // ── Registration Confirmation Email ───────────────────────────
    @Async
    public void sendRegistrationConfirmation(User user, Event event,
                                              String confirmationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Registration Confirmed: " + event.getTitle());
            helper.setText(buildConfirmationHtml(user, event, confirmationCode), true);

            mailSender.send(message);
            logger.info("Confirmation email sent to {}", user.getEmail());

        } catch (Exception e) {
            logger.error("Failed to send confirmation email to {}: {}",
                    user.getEmail(), e.getMessage());
        }
    }

    // ── Cancellation Email ─────────────────────────────────────────
    @Async
    public void sendCancellationEmail(User user, Event event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Registration Cancelled: " + event.getTitle());
            helper.setText(buildCancellationHtml(user, event), true);

            mailSender.send(message);
        } catch (Exception e) {
            logger.error("Failed to send cancellation email: {}", e.getMessage());
        }
    }
    
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ── HTML Email Templates ───────────────────────────────────────
    private String buildConfirmationHtml(User user, Event event, String code) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                  <div style="background: #4F46E5; color: white; padding: 24px; border-radius: 8px 8px 0 0;">
                    <h1 style="margin:0;">✅ Registration Confirmed!</h1>
                  </div>
                  <div style="padding: 24px; background: #f9f9f9;">
                    <p>Hello <strong>%s</strong>,</p>
                    <p>You have successfully registered for:</p>
                    <div style="background: white; border-left: 4px solid #4F46E5;
                                padding: 16px; margin: 16px 0; border-radius: 4px;">
                      <h2 style="margin: 0 0 8px; color: #1a1a1a;">%s</h2>
                      <p style="margin: 4px 0;">📅 <strong>Date:</strong> %s</p>
                      <p style="margin: 4px 0;">📍 <strong>Location:</strong> %s</p>
                      <p style="margin: 4px 0;">🎟️ <strong>Confirmation Code:</strong>
                         <code style="background: #eef2ff; padding: 2px 8px;
                                      border-radius: 4px; font-size: 16px;">%s</code></p>
                    </div>
                    <p>Please save your confirmation code. You may need it at the event entrance.</p>
                    <p style="color: #666; font-size: 14px;">If you need to cancel, please do so
                       at least 24 hours before the event.</p>
                  </div>
                  <div style="background: #eee; padding: 12px; text-align: center;
                              border-radius: 0 0 8px 8px; font-size: 12px; color: #666;">
                    &copy; 2024 EventApp. All rights reserved.
                  </div>
                </body>
                </html>
                """.formatted(
                user.getName(),
                event.getTitle(),
                event.getEventDate().format(FORMATTER),
                event.getLocation(),
                code
        );
    }

    private String buildCancellationHtml(User user, Event event) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;">
                  <div style="background: #EF4444; color: white; padding: 24px; border-radius: 8px 8px 0 0;">
                    <h1 style="margin:0;">❌ Registration Cancelled</h1>
                  </div>
                  <div style="padding: 24px; background: #f9f9f9;">
                    <p>Hello <strong>%s</strong>,</p>
                    <p>Your registration for <strong>%s</strong> on <strong>%s</strong>
                       has been successfully cancelled.</p>
                    <p>We hope to see you at future events!</p>
                  </div>
                </body>
                </html>
                """.formatted(
                user.getName(),
                event.getTitle(),
                event.getEventDate().format(FORMATTER)
        );
    }
}