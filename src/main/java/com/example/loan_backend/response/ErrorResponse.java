package com.example.loan_backend.response;

import org.springframework.validation.FieldError;

public class ErrorResponse {
    public String message;
    public String field;

    public ErrorResponse(FieldError error) {
        this.message = error.getDefaultMessage();
        this.field = error.getField();
    }
}
