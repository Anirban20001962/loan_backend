package com.example.loan_backend.controller;

import java.util.ArrayList;
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


@RequestMapping("/loan")
@RestController
public class LoanController {

  @Autowired
  private UserService userService;

  @Autowired
  private LoanService loanService;


  @PostMapping(value="/reqLoan/{email}")
  public ResponseEntity<Object> addloan(@RequestBody Loan loan, @PathVariable String email){
    List<User> users = userService.getUserByEmail(email);
    loan.setUser(users.get(0));
    loanService.saveLoan(loan);
    return new ResponseEntity<>(true,HttpStatus.OK);
  }

  @GetMapping(value="/getAllLoans/{email}")
  public List<Loan> getMyLoans(@PathVariable String email){
    return loanService.getLoansByUserEmail(email);
  }


}
