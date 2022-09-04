package com.example.loan_backend.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.loan_backend.LoanStatus;
import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.LoanRepository;
import com.example.loan_backend.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
public class LoanServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFirstname("user");
        user.setLastname("xyz");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEmail("user@email.com");
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    @Test
    @Transactional
    public void saveLoan() {
        Loan loan = new Loan();
        loan.setPurpose("for x purpose");
        loan.setAmount(1000);
        loan.setEmimonths(5);
        loan.setInterest(5);
        loan.setUser(user);

        loanService.saveLoan(loan);

        Optional<Loan> loanFound = loanRepository.findById(loan.getId());

        assertTrue(loanFound.isPresent());
        assertTrue(loanFound.get().getStatus() == String.valueOf(LoanStatus.PENDING));
    }

    @Test
    @Transactional
    public void getLoansByUserEmailTest() {
        Loan loan = new Loan();
        loan.setPurpose("for x purpose");
        loan.setAmount(1000);
        loan.setEmimonths(5);
        loan.setInterest(5);
        loan.setUser(user);

        loanService.saveLoan(loan);

        List<Loan> loansFound = loanService.getLoansByUserEmail(user.getEmail());

        assertTrue(loansFound.size() == 1);
    }

    @Test
    @Transactional
    public void acceptLoanByIdTest() {
        Loan loan = new Loan();
        loan.setPurpose("for x purpose");
        loan.setAmount(1000);
        loan.setEmimonths(5);
        loan.setInterest(5);
        loan.setUser(user);
        loan.setUser(user);

        loanService.saveLoan(loan);

        loanService.acceptLoanById(loan.getId());

        Optional<Loan> loanFound = loanRepository.findById(loan.getId());

        assertTrue(loanFound.get().getStatus() == String.valueOf(LoanStatus.ACCEPTED));
    }

    @Test
    @Transactional
    public void rejecttLoanByIdTest() {
        Loan loan = new Loan();
        loan.setPurpose("for x purpose");
        loan.setAmount(1000);
        loan.setEmimonths(5);
        loan.setInterest(5);
        loan.setUser(user);

        loanService.saveLoan(loan);

        loanService.rejectLoanById(loan.getId());

        Optional<Loan> loanFound = loanRepository.findById(loan.getId());

        assertTrue(loanFound.get().getStatus() == String.valueOf(LoanStatus.REJECTED));
    }

}