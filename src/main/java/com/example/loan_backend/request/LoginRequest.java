package com.example.loan_backend.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginRequest {
  @NotNull
  @Email
  public String email;
  @NotNull
  @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "password should be between 4 and 20 with no blank spaces")
  public String password;
}
