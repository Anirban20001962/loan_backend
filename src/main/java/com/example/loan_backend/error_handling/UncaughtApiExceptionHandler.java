package com.example.loan_backend.error_handling;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class UncaughtApiExceptionHandler implements CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request,
            HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex));
        }
        return buildResponseEntity(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error", ex));
    }

    public ResponseEntity<Object> buildResponseEntity(ApiError err) {
        return new ResponseEntity<>(err, err.getHttpStatus());
    }
}
