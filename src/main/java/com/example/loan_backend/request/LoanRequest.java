package com.example.loan_backend.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class LoanRequest {
    @NotNull
    @Positive
    public double amount;
    @NotNull
    @Size(min = 5, max = 100)
    public String purpose;
    @NotNull
    @Positive
    public int emimonths;
    @NotNull
    @Positive
    public double interest;
}
