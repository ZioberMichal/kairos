package com.kairos.project.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetConfirmRequest {
    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;
}
