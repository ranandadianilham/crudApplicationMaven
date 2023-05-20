package com.java.crudApplicationMaven.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
@SuppressWarnings("all")
@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String BASE_PATH = "/api/v1/auth";

    @Test
    void register() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(BASE_PATH+"/authenticate");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals("hello ", result.getResponse().getContentAsString());
    }

    @Test
    void authenticate() {
    }
}