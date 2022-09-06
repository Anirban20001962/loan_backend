package com.example.loan_backend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.CustomUserDetails;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.UserRepository;
import com.example.loan_backend.util.JwtUtil;

@AutoConfigureMockMvc
@SpringBootTest
public class AdminControllerTests {
    @Autowired
    private MockMvc server;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtString;

    @BeforeEach
    private void setup() {
        User user = new User();

        user.setFirstname("Anirban");
        user.setLastname("Mukherjee");
        user.setEmail("abcd@gmail.com");
        user.setRole(AccountRoles.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode("messi"));

        userRepository.save(user);

        this.jwtString = jwtUtil.generateToken(new CustomUserDetails(user));
    }

    public void checkAllUsers() {

    }
}
