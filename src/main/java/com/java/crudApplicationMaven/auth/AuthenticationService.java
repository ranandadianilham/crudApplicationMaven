package com.java.crudApplicationMaven.auth;

import com.java.crudApplicationMaven.config.JwtService;
import com.java.crudApplicationMaven.constant.enumeration.Role;

import com.java.crudApplicationMaven.model.User;
import com.java.crudApplicationMaven.repo.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepo repo;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                String encodedPass = request.getPassword().equals("") ? request.getPassword()
                                : passwordEncoder.encode(request.getPassword());

                var userTokenBuild = User.builder()
                                .email(request.getEmail())
                                .username(request.getEmail())
                                .password(encodedPass)
                                .role(Role.USER)
                                .build();

                repo.save(userTokenBuild);

                var jwtToken = jwtService.generateToken(userTokenBuild);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repo.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }
}
