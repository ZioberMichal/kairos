package com.kairos.core.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggingEmailService implements EmailService {

    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        // For development we just log the reset link. Configure a real SMTP sender in production.
        log.info("Password reset requested for {}. Reset link: {}", to, resetLink);
    }
}
