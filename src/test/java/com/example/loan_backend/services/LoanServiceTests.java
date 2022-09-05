package com.example.loan_backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.LoanRepository;
import com.example.loan_backend.repositories.UserRepository;

// Unit test for loan service
@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private LoanService loanService;

    private Loan loan1;
    private Loan loan2;
    private User user;

    private List<Loan> loans;

    @BeforeEach
    public void setUp() {
        loans = new ArrayList<>();
        loan1 = new Loan();
        loan2 = new Loan();
        user = new User();

        user.setEmail("loanService@test.com");

        loan1.setUser(user);
        loan1.setUser(user);

        loans.add(loan1);
        loans.add(loan2);

        user.setLoans(loans);

    }

    @AfterEach
    public void tearDown() {
        loans = null;
        loan1 = null;
        loan2 = null;
        user = null;
    }

    @Test
    public void saveLoanTest() {

        when(loanRepository.save(any())).thenReturn(null);
        loanService.saveLoan(loan1);

        assertTrue(true);
    }

    @Test
    public void getAllLoansTest() {

        when(loanRepository.findAll()).thenReturn(loans);
        List<Loan> foundLoans = loanService.getAllLoans();

        assertEquals(2, foundLoans.size());
    }

    @Test
    public void getLoanById() {

        when(loanRepository.findById(loan1.getId())).thenReturn(Optional.of(loan1));

        Optional<Loan> found = loanService.getLoanById(loan1.getId());

        assertEquals(true, found.get().getId() == loan1.getId());
    }

    @Test
    public void getLoansByUserEmailTest() {
        when(userRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

        List<Loan> foundLoans = loanService.getLoansByUserEmail(user.getEmail());

        assertEquals(2, foundLoans.size());
    }

    @Test
    public void getAllPendingLoans() {
        when(loanRepository.findAllByStatus(any())).thenReturn(loans);

        List<Loan> found = loanService.getAllPendingLoans();
        assertEquals(2, found.size());

    }

    @Test
    public void acceptLoanByIdTest() {
        when(loanRepository.findById(loan1.getId())).thenReturn(Optional.of(loan1));

        loanService.acceptLoanById(loan1.getId());
        assertTrue(true);
    }

    @Test
    public void rejectLoanByIdTest() {
        when(loanRepository.findById(loan1.getId())).thenReturn(Optional.of(loan1));

        loanService.rejectLoanById(loan1.getId());
        assertTrue(true);
    }
}