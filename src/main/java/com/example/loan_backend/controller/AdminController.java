package com.example.loan_backend.controller;

import java.util.UUID;

import javax.validation.constraints.Email;

import com.example.loan_backend.response.MsgDataResponse;
import com.example.loan_backend.services.LoanService;
import com.example.loan_backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Validated
@SecurityRequirement(name = "bearer-key")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoanService loanService;

    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(new MsgDataResponse("All Users", userService.getAllUsers()), HttpStatus.OK);
    }

    @GetMapping("/getSearchUsersByFirstName/{firstname}")
    public ResponseEntity<?> getUsersStartsWithFirstName(@PathVariable String firstname) {
        return new ResponseEntity<>(new MsgDataResponse("All Users", userService.getUsersByFirstName(firstname)),
                HttpStatus.OK);
    }

    @GetMapping("/getSearchUsersByLastName/{lastname}")
    public ResponseEntity<?> getUsersStartsWithLastName(@PathVariable String lastname) {
        return new ResponseEntity<>(new MsgDataResponse("All Users", userService.getUsersByLastName(lastname)),
                HttpStatus.OK);
    }

    @GetMapping("/getSearchUsersByEmail/{email}")
    public ResponseEntity<?> getUsersStartsWithEmail(@PathVariable String email) {
        return new ResponseEntity<>(new MsgDataResponse("All Users", userService.getUsersByEmail(email)),
                HttpStatus.OK);
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
}
