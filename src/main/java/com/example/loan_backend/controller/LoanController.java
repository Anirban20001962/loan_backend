package com.example.loan_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.loan_backend.error_handling.EntityNotFoundException;

@RestController
@RequestMapping("/test")
public class LoanController {
  @GetMapping("")
  public String checkAccess() throws EntityNotFoundException {
    throw new EntityNotFoundException("Not Found");
  }
}
