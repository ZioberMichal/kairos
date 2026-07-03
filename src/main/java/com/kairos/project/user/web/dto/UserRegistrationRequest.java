package com.kairos.project.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String password;

    private String displayName;
}
