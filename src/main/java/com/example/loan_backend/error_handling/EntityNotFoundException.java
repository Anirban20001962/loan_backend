package com.example.loan_backend.error_handling;

public class EntityNotFoundException extends RuntimeException {
    private String message;

    public EntityNotFoundException(String msg) {
        message = msg;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
