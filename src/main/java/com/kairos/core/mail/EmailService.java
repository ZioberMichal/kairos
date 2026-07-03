package com.kairos.core.mail;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetLink);
}
