package com.example.loan_backend.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;

import com.example.loan_backend.models.User;
import com.example.loan_backend.response.MsgDataResponse;
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
    public ResponseEntity<?> getAllLoans() {
        return new ResponseEntity<>(new MsgDataResponse("All Loans", loanService.getAllLoans()), HttpStatus.OK);
    }

    @GetMapping(value = "/getLoansByUser/{email}")
    public ResponseEntity<?> getLoansByUser(@PathVariable @Email String email) {
        return new ResponseEntity<>(new MsgDataResponse("All User Loans", loanService.getLoansByUserEmail(email)),
                HttpStatus.OK);
    }

    @PutMapping(value = "/acceptLoan/{loan_id}")
    public ResponseEntity<?> acceptLoan(@PathVariable UUID loan_id) {
        loanService.acceptLoanById(loan_id);
        return new ResponseEntity<>("Loan Accepted Successfully", HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/rejectLoan/{loan_id}")
    public ResponseEntity<Object> rejectLoan(@PathVariable UUID loan_id) {
        loanService.rejectLoanById(loan_id);
        return new ResponseEntity<>("Loan Rejected Successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getPendingLoans")
    public ResponseEntity<?> getPendingLoans() {
        return new ResponseEntity<>(new MsgDataResponse("All Pending Loans", loanService.getAllPendingLoans()),
                HttpStatus.OK);
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
