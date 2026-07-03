package com.kairos.project.user.web;

import com.kairos.core.web.ApiConstants;
import com.kairos.project.user.model.PasswordRecoveryService;
import com.kairos.project.user.model.UserService;
import com.kairos.project.user.web.dto.PasswordRecoveryRequest;
import com.kairos.project.user.web.dto.PasswordResetConfirmRequest;
import com.kairos.project.user.web.dto.UserRegistrationRequest;
import com.kairos.project.user.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kairos.core.web.ApiConstants.Users.*;

@RestController
@RequestMapping(value = ApiConstants.SERVICE + "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final PasswordRecoveryService recoveryService;
    private final UserMapper userMapper;

    @PostMapping(REGISTER)
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationRequest request) {
        var user = userService.register(request.getUsername(), request.getPassword(), request.getDisplayName());
        var resp = userMapper.toResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping(RECOVER_REQUEST)
    public ResponseEntity<Void> requestRecover(@RequestBody PasswordRecoveryRequest request) {
        recoveryService.requestPasswordReset(request.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping(RECOVER_CONFIRM)
    public ResponseEntity<Void> confirmRecover(@RequestBody PasswordResetConfirmRequest request) {
        var ok = recoveryService.resetPassword(request.getToken(), request.getNewPassword());
        return ok ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
