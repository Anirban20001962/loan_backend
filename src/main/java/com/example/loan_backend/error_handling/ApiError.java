package com.example.loan_backend.error_handling;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.DEDUCTION, property = "error", visible = true)
public class ApiError {
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private Object debugMessage;
    private List<ApiSubErrors> subErrors;

    // Constructors
    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        httpStatus = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this(status);
        message = "Unexpected Error";
        debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String msg, Throwable ex) {
        this(status);
        message = msg;
        debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String msg, Object debug) {
        this(status);
        message = msg;
        this.debugMessage = debug;
    }

    // Getters and Setters
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDebugMessage() {
        return this.debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<ApiSubErrors> getSubErrors() {
        return this.subErrors;
    }

    public void setSubErrors(List<ApiSubErrors> subErrors) {
        this.subErrors = subErrors;
    }

}
