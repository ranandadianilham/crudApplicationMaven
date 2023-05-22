package com.java.crudApplicationMaven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.crudApplicationMaven.constant.enumeration.ResponseStatusCode;
import com.java.crudApplicationMaven.model.Post;
import com.java.crudApplicationMaven.payload.request.PostGetAllRequest;
import com.java.crudApplicationMaven.repo.PostRepo;
import com.java.crudApplicationMaven.service.PostService;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* @WebMvcTest(BlogPostController.class) */
//@WebMvcTest(BlogPostController.class)
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class) // Apply the SpringExtension
@SpringBootTest // Load the Spring application context
class BlogPostControllerTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private ObjectMapper objectMapper;

        private MockMvc mockMvc;

        @BeforeEach
        public void setup() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

        private static final String SECRET_KEY = "6150645367566B5970337336763979244226452948404D6251655468576D5A71";

        @Test
        public void testGetAllPosts() throws Exception {
                // Prepare the request body
                PostGetAllRequest request = new PostGetAllRequest();
                request.setSortType("ASC");
                request.setSortBy("title");

                // Perform the request
                ResultActions resultActions = mockMvc.perform(post("/blog/post/list/all")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)));

                // Verify the response
                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()))
                                .andExpect(jsonPath("$.data").exists());
        }

        @Test
        public void testGetAllPostsPaginated() throws Exception {
                // Prepare the request body
                PostGetAllRequest request = new PostGetAllRequest();
                request.setSortType("ASC");
                request.setPageNo(0);
                request.setRowPerPage(10);
                request.setSortBy("title");

                // Perform the request
                ResultActions resultActions = mockMvc.perform(post("/blog/post/list/paginated")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)));

                // Verify the response
                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()))
                                .andExpect(jsonPath("$.data").exists());
        }

        @Test
        public void testGetPostById() throws Exception {
                // Prepare the request path variable
                Long postId = 1L;

                // Perform the request
                ResultActions resultActions = mockMvc.perform(get("/blog/post/{id}", postId));

                // Verify the response
                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()))
                                .andExpect(jsonPath("$.data").exists());
        }

        @Test
        public void testAddPost() throws Exception {
                // Prepare the request body
                Post postCase = new Post();
                postCase.setId(1L);
                postCase.setTitle("New Post");
                postCase.setAuthor("John Doe");
                postCase.setBody("Lorem ipsum");

                String jwtToken = generateJwtToken();

                // Perform the request
                ResultActions resultActions = mockMvc.perform(post("/blog/post/add")
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postCase)));

                // Verify the response
                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()))
                                .andExpect(jsonPath("$.data").exists());
        }

        @Test
        public void testUpdatePostById() throws Exception {
                Post postCase = new Post();
                postCase.setId(1L);
                postCase.setTitle("New Post");
                postCase.setAuthor("John Doe");
                postCase.setBody("Lorem ipsum");

                String jwtToken = generateJwtToken();

                // Perform the request
                ResultActions resultActions = mockMvc.perform(post("/blog/post/update/" + 1)
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postCase)));

                // Verify the response
                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()))
                                .andExpect(jsonPath("$.data").exists());
        }

        @Test
        public void testDeletePostById() throws Exception {
                String jwtToken = generateJwtToken();
                ResultActions resultActions = mockMvc.perform(post("/blog/post/delete/" + 1)
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON));

                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.code").value(ResponseStatusCode.SUCCESS.code()))
                                .andExpect(jsonPath("$.desc").value(ResponseStatusCode.SUCCESS.desc()));
        }

        private static String generateJwtToken() {
                // String secretKey =
                // "6150645367566B5970337336763979244226452948404D6251655468576D5A71"; //
                // Replace with your
                // secret key

                // Define token claims
                Map<String, Object> claims = new HashMap<>();
                claims.put("username", "testuser");
                claims.put("role", "user");

                // Set token expiration time
                long currentTimeMillis = System.currentTimeMillis();
                long expirationTimeMillis = currentTimeMillis + (60 * 60 * 1000); // 1 hour
                Date expirationDate = new Date(expirationTimeMillis);

                // Generate the token
                String token = Jwts.builder()
                                .setClaims(claims)
                                .setExpiration(expirationDate)
                                /* .signWith(SignatureAlgorithm.HS256, secretKey) */
                                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                                .compact();

                return token;
        }

        private static Key getSignInKey() {
                byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
                return Keys.hmacShaKeyFor(keyBytes);
        }

}