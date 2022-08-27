package com.example.loan_backend.response;

public class LoginResponse {
  public String jwt;

  public LoginResponse(String jwt) {
    this.jwt = jwt;
  }
}
