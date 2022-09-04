package com.example.loan_backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loan_backend.LoanStatus;
import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;
import com.example.loan_backend.repositories.LoanRepository;
import com.example.loan_backend.repositories.UserRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanrepo;
    @Autowired
    private UserRepository userRepository;

    // returns list of loans
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<Loan>();
        loanrepo.findAll().forEach(loans::add);
        return loans;
    }

    // save loan
    public void saveLoan(Loan l) {
        l.setStatus(LoanStatus.PENDING);
        loanrepo.save(l);
    }

    // get loan by id
    public Optional<Loan> getLoanById(UUID id) {
        return loanrepo.findById(id);
    }

    // delete loan by id
    public void deleteLoanById(UUID id) {
        loanrepo.deleteById(id);
    }

    // Gets all loans of a particular user
    public List<Loan> getLoansByUserEmail(String email) {
        if (email == null)
            throw new EntityNotFoundException("Email should not be null");

        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        user.orElseThrow(() -> new EntityNotFoundException("user not found"));

        return user.get().getLoans();
    }

    // Gets All PendingLoans
    public List<Loan> getAllPendingLoans() {
        List<Loan> allPendingLoans = new ArrayList<>();
        loanrepo.findAllByStatus(String.valueOf(LoanStatus.PENDING)).forEach(allPendingLoans::add);

        return allPendingLoans;
    }

    // Accept loan(admin)
    public void acceptLoanById(UUID id) {
        Optional<Loan> oldloan = loanrepo.findById(id);

        oldloan.orElseThrow(() -> new EntityNotFoundException("Loan Not Found"));

        Loan newloan = oldloan.get();
        newloan.setStatus(LoanStatus.ACCEPTED);
        loanrepo.save(newloan);

    }

    // Reject Loan(admin)
    public void rejectLoanById(UUID id) {
        Optional<Loan> oldloan = loanrepo.findById(id);

        oldloan.orElseThrow(() -> new EntityNotFoundException("Loan Not Found"));

        Loan newloan = oldloan.get();
        newloan.setStatus(LoanStatus.REJECTED);
        loanrepo.save(newloan);
    }
}
