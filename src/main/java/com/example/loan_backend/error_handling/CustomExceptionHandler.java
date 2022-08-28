package com.example.loan_backend.error_handling;

import org.springframework.http.ResponseEntity;

public interface CustomExceptionHandler {
    public ResponseEntity<Object> buildResponseEntity(ApiError err);
}
