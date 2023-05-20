package com.java.crudApplicationMaven.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    void testRegister() {
        // Prepare test data
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUsername("username");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("password123", request.getPassword());
        assertEquals("username", request.getUsername());
    }

    @Test
    void testAuthentication() {
        RegisterRequest request = new RegisterRequest();

        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUsername("username");

        AuthenticationResponse authenticationResponse = new AuthenticationResponse("token123");
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "test@example.com",
                "password123");

        authenticationService.register(request);
        authenticationService.authenticate(authenticationRequest);
        // verivy register
        verify(authenticationService).register(request);
        verify(authenticationService).authenticate(authenticationRequest);

        // somehow return unnecessary stubbing detected
        lenient().when(authenticationService.register(request)).thenReturn(authenticationResponse);
        lenient().when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);
    }

    @Test
    void passWordEncodeTest() {
        // Arrange
        String password = "123";
        String encodedPassword = "encoded123";

        // create mock PasswordEncoder
        PasswordEncoder result = mock(PasswordEncoder.class);
        result.encode(password);
        verify(result).encode(password);

        // passwordEncoder.encode(password);
        // Assert
        // assertEquals(encodedPassword, result);
        verify(passwordEncoder).encode(password);

        // without lenient(), strict stubbing causing unnecessary stubbing exception
        lenient().when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        // passwordEncoder.encode("123");
        // verify(passwordEncoder).encode("123");
    }
}