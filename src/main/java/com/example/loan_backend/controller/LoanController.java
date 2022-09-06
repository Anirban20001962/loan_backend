package com.example.loan_backend.controller;

import com.example.loan_backend.models.Loan;
import com.example.loan_backend.request.LoanRequest;
import com.example.loan_backend.response.MsgDataResponse;
import com.example.loan_backend.services.LoanService;
import com.example.loan_backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/loan")
@RestController
@SecurityRequirement(name = "bearer-key")
public class LoanController {

  @Autowired
  private UserService userService;

  @Autowired
  private LoanService loanService;

  @PostMapping(value = "/reqLoan")
  public ResponseEntity<Object> addloan(@Valid @RequestBody LoanRequest lr) {

    Loan loan = new Loan(lr);

    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    loan.setUser(userService.getUniqueUserByEmail(email).get());
    loanService.saveLoan(loan);
    return new ResponseEntity<>(loan, HttpStatus.CREATED);
  }

  @GetMapping(value = "/getAllLoans")
  public ResponseEntity<?> getMyLoans() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return new ResponseEntity<>(new MsgDataResponse("All Loans", loanService.getLoansByUserEmail(email)),
        HttpStatus.OK);
  }

}
