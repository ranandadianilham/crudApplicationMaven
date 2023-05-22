package com.java.crudApplicationMaven.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

//@WebMvcTest(AuthenticationController.class)
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationResponse authenticationResponse = new AuthenticationResponse("token123");

    @Test
    void registerTest() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUsername("username");

        authenticationService.register(request);

        verify(authenticationService).register(request);
        lenient().when(authenticationService.register(request)).thenReturn(authenticationResponse);
    }

    @Test
    void authenticationTest() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "test@example.com",
                "password123");

        authenticationService.authenticate(authenticationRequest);

        verify(authenticationService).authenticate(authenticationRequest);

        lenient().when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);
    }
}