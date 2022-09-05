package com.example.loan_backend.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoanRequest {
    @NotNull
    public double amount;
    @Size(min = 5, max = 100)
    public String purpose;
    @NotNull
    public int emimonths;
    @NotNull
    public double interest;
}
