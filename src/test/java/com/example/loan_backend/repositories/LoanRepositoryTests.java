package com.example.loan_backend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.loan_backend.AccountRoles;
import com.example.loan_backend.LoanStatus;
import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;

// Integration Testing for Loan Repository
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LoanRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanRepository loanRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setRole(AccountRoles.ROLE_USER);
        user.setFirstname("loan");
        user.setLastname("service");
        user.setPassword("123456");
        user.setEmail("loanRepo@test.com");

        user = userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        loanRepository.deleteAll();
        userRepository.deleteAll();
        user = null;
    }

    @Test
    public void findAllByStatusTest() {
        Loan pLoan = makeLoan(user, LoanStatus.PENDING);
        Loan rLoan = makeLoan(user, LoanStatus.REJECTED);
        Loan aLoan = makeLoan(user, LoanStatus.ACCEPTED);

        loanRepository.save(pLoan);
        loanRepository.save(rLoan);
        loanRepository.save(aLoan);

        List<Loan> pLoans = (List<Loan>) loanRepository.findAllByStatus(String.valueOf(LoanStatus.PENDING));
        List<Loan> rLoans = (List<Loan>) loanRepository.findAllByStatus(String.valueOf(LoanStatus.REJECTED));
        List<Loan> aLoans = (List<Loan>) loanRepository.findAllByStatus(String.valueOf(LoanStatus.ACCEPTED));

        assertEquals(1, pLoans.size());
        assertEquals(1, rLoans.size());
        assertEquals(1, aLoans.size());

    }

    private Loan makeLoan(User user, LoanStatus status) {
        Loan loan = new Loan();
        loan.setStatus(status);
        loan.setPurpose("for x purpose");
        loan.setAmount(1000);
        loan.setEmimonths(5);
        loan.setInterest(5);
        loan.setUser(user);

        return loan;

    }
}
