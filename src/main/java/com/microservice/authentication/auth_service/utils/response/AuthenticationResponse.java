package com.microservice.authentication.auth_service.utils.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationResponse {
    private String token;
}
