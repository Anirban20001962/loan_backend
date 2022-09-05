package com.example.loan_backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.example.loan_backend.util.JsonMaker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTests {
        @Autowired
        private MockMvc server;
        private MediaType mediaType = MediaType.APPLICATION_JSON;

        @Test
        public void testSignUp() throws Exception {
                // signup data for admin
                String content = new JsonMaker().add("email", "user2@test.com")
                                .add("password", "password")
                                .add("firstname", "user1")
                                .add("lastname", "meow").build();

                RequestBuilder req = post("/auth/signup").contentType(mediaType).content(content).accept(mediaType);

                server.perform(req).andExpect(status().isCreated());
        }

        @Test
        public void testSignIn() throws Exception {
                String content = new JsonMaker().add("email",
                                "user@test.com").add("password", "password").add("firstname",
                                                "user1")
                                .add("lastname", "meow").build();

                RequestBuilder req = post("/auth/signup").contentType(mediaType).content(content).accept(mediaType);

                server.perform(req).andReturn();

                // signup data for admin
                content = new JsonMaker().add("email",
                                "user@test.com").add("password", "password").build();

                req = post("/auth/signin").contentType(mediaType).content(content).accept(mediaType);

                server.perform(req).andExpect(status().isOk());
        }
}
