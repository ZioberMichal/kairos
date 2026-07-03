package com.kairos.project.user.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderProxy {
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderProxy(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String raw) {
        return passwordEncoder.encode(raw);
    }
}
