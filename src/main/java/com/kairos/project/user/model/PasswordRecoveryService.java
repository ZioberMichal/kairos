package com.kairos.project.user.model;

import com.kairos.core.mail.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordRecoveryService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoderProxy passwordEncoderProxy;

    private final Duration TOKEN_LIFETIME = Duration.ofHours(1);

    public void requestPasswordReset(String username) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            // Do not reveal existence
            return;
        }
        var token = UUID.randomUUID().toString();
        var expires = Instant.now().plus(TOKEN_LIFETIME);
        var entity = new PasswordResetToken();
        entity.setToken(token);
        entity.setUser(user);
        entity.setExpiresAt(expires);
        entity.setUsed(false);
        tokenRepository.save(entity);
        var resetLink = String.format("/reset-password?token=%s", token);
        emailService.sendPasswordResetEmail(user.getUsername(), resetLink);
    }

    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> opt = tokenRepository.findByToken(token);
        if (opt.isEmpty()) return false;
        var t = opt.get();
        if (t.isUsed() || t.getExpiresAt().isBefore(Instant.now())) return false;
        var user = t.getUser();
        user.setPassword(passwordEncoderProxy.encode(newPassword));
        userRepository.save(user);
        t.setUsed(true);
        tokenRepository.save(t);
        return true;
    }
}
