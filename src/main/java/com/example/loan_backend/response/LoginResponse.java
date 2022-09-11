package com.example.loan_backend.response;

import com.example.loan_backend.models.User;

public class LoginResponse {
  public String jwt;
  public User user;

  public LoginResponse(String jwt, User user) {
    this.jwt = jwt;
    this.user = user;
  }
}
