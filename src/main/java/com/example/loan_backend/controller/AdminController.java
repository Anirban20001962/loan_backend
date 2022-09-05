package com.example.loan_backend.controller;

import java.util.List;
import java.util.UUID;

import com.example.loan_backend.models.Loan;
import com.example.loan_backend.models.User;
import com.example.loan_backend.services.LoanService;
import com.example.loan_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoanService loanService;

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/getAllLoans")
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping(value = "/getLoansByUser/{email}")
    public List<Loan> getLoansByUser(@PathVariable String email) {
        return loanService.getLoansByUserEmail(email);
    }

    @PutMapping(value = "/acceptLoan/{loan_id}")
    public ResponseEntity<Object> acceptLoan(@PathVariable UUID loan_id) {
        loanService.acceptLoanById(loan_id);
        return new ResponseEntity<>("Loan Accepted Successfully", HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/rejectLoan/{loan_id}")
    public ResponseEntity<Object> rejectLoan(@PathVariable UUID loan_id) {
        loanService.rejectLoanById(loan_id);
        return new ResponseEntity<>("Loan Rejected Successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getPendingLoans")
    public List<Loan> getPendingLoans() {
        return loanService.getAllPendingLoans();
    }
    
    @GetMapping(value = "/getUsersByEmail/{email}")
    public List<User> getUsersByEmail(@PathVariable String email) {
        return userService.getUsersByEmail(email);
    }
    
    @GetMapping(value = "/getUsersByFirstName/{firstname}")
    public List<User> getUsersByFirstName(@PathVariable String firstname) {
        return userService.getUsersByFirstName(firstname);
    }
    
    @GetMapping(value = "/getUsersByLastName/{lastname}")
    public List<User> getUsersByLastName(@PathVariable String lastname) {
        return userService.getUsersByLastName(lastname);
    }
}
