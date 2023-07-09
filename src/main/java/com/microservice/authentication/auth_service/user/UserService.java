package com.microservice.authentication.auth_service.user;

import com.microservice.authentication.auth_service.requests.Login;
import com.microservice.authentication.auth_service.requests.Register;
import com.microservice.authentication.auth_service.roles.Roles;
import com.microservice.authentication.auth_service.utils.config.JwtService;
import com.microservice.authentication.auth_service.utils.response.AuthenticationResponse;
import com.microservice.authentication.auth_service.utils.response.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse signup(Register register) {
        try {
            var user = User.builder()
                    .firstName(register.getFirstName())
                    .lastName(register.getLastName())
                    .email(register.getEmail())
                    .password(passwordEncoder.encode(register.getPassword()))
                    .roles(Roles.USER)
                    .build();

            User addUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken).build();
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }

    public AuthenticationResponse signin(Login request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()

                    )
            );
            var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken).build();
        } catch (Exception e) {
            log.info("Catched Error {} " + e);
            return null;
        }
    }
}
