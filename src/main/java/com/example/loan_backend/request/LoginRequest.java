package com.example.loan_backend.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class LoginRequest {
  @Email
  public String email;
  @Size(max = 20, min = 5)
  public String password;
}
