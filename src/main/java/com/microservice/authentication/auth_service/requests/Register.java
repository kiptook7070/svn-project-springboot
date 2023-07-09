package com.microservice.authentication.auth_service.requests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Register {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
