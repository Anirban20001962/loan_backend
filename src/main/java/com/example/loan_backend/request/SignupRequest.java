package com.example.loan_backend.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupRequest {
    @NotNull
    @Size(min = 2, max = 20, message = "lastname should be between 2 and 20")
    public String firstname;
    @NotNull
    @Size(min = 2, max = 20, message = "lastname should be between 2 and 20")
    public String lastname;
    @NotNull
    @Email
    public String email;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}", message = "password should be between 4 and 20 with no blank spaces")
    public String password;
}
