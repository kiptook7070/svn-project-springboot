package com.microservice.authentication.auth_service.roles;

import lombok.*;

public enum Roles {
    USER,
    ADMIN;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class UserRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }
}
