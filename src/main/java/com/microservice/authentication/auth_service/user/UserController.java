package com.microservice.authentication.auth_service.user;

import com.microservice.authentication.auth_service.requests.Login;
import com.microservice.authentication.auth_service.requests.Register;
import com.microservice.authentication.auth_service.utils.response.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody Register register) {
        try {
            return ResponseEntity.ok(userService.signup(register));
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody Login request) {
        try {
            return ResponseEntity.ok().body(userService.signin(request));
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
