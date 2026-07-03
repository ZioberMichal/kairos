package com.kairos.project.user.model;

import com.kairos.core.mail.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String username, String rawPassword, String displayName) {
        var existing = userRepository.findByUsername(username);
        if (existing != null) {
            throw new IllegalArgumentException("User with username already exists");
        }

        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(Collections.emptyList());

        // try to assign default USER role if present
        roleRepository.findByCode("USER").ifPresent(role -> user.setRoles(Collections.singletonList(role)));

        return userRepository.save(user);
    }

}
