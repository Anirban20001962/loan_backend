package com.example.loan_backend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.RequestBuilder;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;
import com.example.loan_backend.util.JsonMaker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class LoanControllerTests {
    @Autowired
    // private MockMvc server;
    private MediaType mediaType = MediaType.APPLICATION_JSON;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    // private String jwtTokenString;

    @BeforeEach
    private void setUp() throws Exception {
        User user = new User();
        user.setEmail("loanController@test.com");
        user.setFirstname("loan");
        user.setLastname("controller");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(AccountRoles.ROLE_USER);

        userRepository.save(user);

        String content = new JsonMaker().add("email", user.getEmail()).add("password", "123456").build();

        post("/auth/signin").contentType(mediaType).content(content).accept(mediaType);

        //

    }
}
