package com.microservice.authentication.auth_service.requests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Login {
    private String email;
    private String password;
}
