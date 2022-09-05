package com.example.loan_backend.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class SignupRequest {
    @Size(min = 4, max = 20)
    public String firstname;
    @Size(min = 4, max = 20)
    public String lastname;
    @Email
    public String email;
    @Size(min = 4, max = 20)
    public String password;
}
