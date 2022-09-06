package com.example.loan_backend.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.CustomUserDetails;
import com.example.loan_backend.LoanStatus;
import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.LoanRepository;
import com.example.loan_backend.repositories.UserRepository;
import com.example.loan_backend.util.JsonMaker;
import com.example.loan_backend.util.JwtUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTests {

    @Autowired
    private MockMvc server;

    private MediaType mediaType = MediaType.APPLICATION_JSON;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtTokenString;

    @BeforeEach
    private void setUp() throws Exception {
        User user = new User();
        user.setEmail("loanController@test.com");
        user.setFirstname("loan");
        user.setLastname("controller");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(AccountRoles.ROLE_USER);

        userRepository.save(user);

        jwtTokenString = "Bearer " + jwtUtil.generateToken(new CustomUserDetails(user));

        Loan loan = new Loan();
        loan.setAmount(200);
        loan.setInterest(12);
        loan.setEmimonths(12);
        loan.setPurpose("something");
        loan.setStatus(LoanStatus.PENDING);
        loan.setUser(user);

        loanRepository.save(loan);
    }

    @Test
    public void testReqLoan() throws Exception {
        String content = new JsonMaker().add("amount", 200)
                .add("purpose", "something")
                .add("emimonths", 12)
                .add("interest", 4.2)
                .build();

        RequestBuilder req = post("/loan/reqLoan")
                .contentType(mediaType)
                .header("Authorization", jwtTokenString)
                .content(content)
                .accept(mediaType);

        server.perform(req).andExpect(status().isCreated());

    }

    @Test
    public void testGetAll() throws Exception {
        RequestBuilder req = get("/loan/getAllLoans")
                .contentType(mediaType)
                .header("Authorization", jwtTokenString)
                .accept(mediaType);

        server.perform(req).andExpect(status().isOk());
    }

    @AfterEach
    private void clear() {
        loanRepository.deleteAll();
        userRepository.deleteAll();
        jwtTokenString = "";
    }
}
