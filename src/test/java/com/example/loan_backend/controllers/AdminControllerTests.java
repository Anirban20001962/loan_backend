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
import com.example.loan_backend.util.JwtUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
    private LoanRepository loanRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private Loan loan;

    private MediaType mediaType = MediaType.APPLICATION_JSON;

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

        this.jwtString = "Bearer " + jwtUtil.generateToken(new CustomUserDetails(user));

        loan = new Loan();

        user.setEmail("abcddifferent@gmail.com");
        user.setRole(AccountRoles.ROLE_USER);

        loan.setAmount(200);
        loan.setEmimonths(12);
        loan.setInterest(5);
        loan.setPurpose("something");
        loan.setUser(user);
        loan.setStatus(LoanStatus.PENDING);

        loanRepository.save(loan);
    }

    @Test
    public void checkAllUsers() throws Exception {
        RequestBuilder req = get("/admin/users")
                .header("Authorization", this.jwtString)
                .contentType(this.mediaType)
                .accept(this.mediaType);

        server.perform(req).andExpect(status().isOk());
    }

    @Test
    public void checkAcceptLoan() throws Exception {

        RequestBuilder req = put("/admin/acceptLoan/" + loan.getId())
                .header("Authorization", this.jwtString)
                .contentType(mediaType)
                .accept(mediaType);

        server.perform(req).andExpect(status().isAccepted());

        Loan newLoan = loanRepository.findById(loan.getId()).get();
        assertTrue(newLoan.getStatus()
                .equalsIgnoreCase(String.valueOf(LoanStatus.ACCEPTED)));
    }

    @Test
    public void checkRejectLoan() throws Exception {

        RequestBuilder req = put("/admin/rejectLoan/" + loan.getId())
                .header("Authorization", this.jwtString)
                .contentType(mediaType)
                .accept(mediaType);

        server.perform(req).andExpect(status().isAccepted());

        Loan newLoan = loanRepository.findById(loan.getId()).get();
        assertTrue(newLoan.getStatus()
                .equalsIgnoreCase(String.valueOf(LoanStatus.REJECTED)));
    }

    @Test
    public void checkGetPendingLoans() throws Exception {

        RequestBuilder req = get("/admin/getPendingLoans")
                .header("Authorization", this.jwtString)
                .contentType(mediaType)
                .accept(mediaType);

        server.perform(req).andExpect(status().isOk());
    }

    @AfterEach
    public void clear() {
        this.loanRepository.deleteAll();
        this.userRepository.deleteAll();
        this.loan = null;
    }
}
