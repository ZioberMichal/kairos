package com.kairos.project.user.web;

import com.kairos.project.user.model.User;
import com.kairos.project.user.web.dto.UserResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        var roles = user.getRoles() == null ? java.util.List.of() : user.getRoles().stream().map(r -> r.getCode()).collect(Collectors.toList());
        var resp = new UserResponse(user.getId(), user.getUsername(), null, roles);
        return resp;
    }
}
