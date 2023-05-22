package com.java.crudApplicationMaven.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.crudApplicationMaven.constant.enumeration.ResponseStatusCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(AuthenticationController.class)
@SpringBootTest
class AuthenticationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void registerTest() throws JsonProcessingException, Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setUsername("username");

        // Perform the request
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void authenticationTest() throws JsonProcessingException, Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                "test@example.com",
                "password123");

        // Perform the request
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)));

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()))
                .andExpect(jsonPath("$.data").exists());
    }
}