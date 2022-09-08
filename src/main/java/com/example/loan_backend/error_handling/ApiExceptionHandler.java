package com.example.loan_backend.error_handling;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.loan_backend.response.ErrorResponse;

import io.jsonwebtoken.JwtException;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler
        implements CustomExceptionHandler {

    @Override
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Path deosn't exist", ex));
    }

    @Override
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        return buildResponseEntity(new ApiError(HttpStatus.METHOD_NOT_ALLOWED, "Method not found", ex));
    }

    @Override
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST, "Data Validation Error",
                        ex.getFieldErrors().stream().map(ErrorResponse::new).toList()));
    }

    @Override
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST, "Invalid Request Value", ex));
    }

    /** Custom Exceptions are placed here */

    /** Should be thrown when an entity in database is not found */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex));
    }

    /** Authentication errors */
    @ExceptionHandler({ InsufficientAuthenticationException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleInsufficientAuthExpections(
            InsufficientAuthenticationException ex) {

        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, "Access Denied", ex));
    }

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleOtherAuthException(BadCredentialsException ex,
            WebRequest webRequest) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, "Access Denied", ex));
    }

    @ExceptionHandler({ BadCredentialsException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadCredException(RuntimeException ex,
            WebRequest webRequest) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Bad Credentials", ex));
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleViolationException(RuntimeException ex,
            WebRequest webRequest) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Data validation Error", ex));
    }

    /** All JwtExceptions */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleJwtException(Exception ex, WebRequest request) {
        return buildResponseEntity(
                new ApiError(HttpStatus.UNAUTHORIZED, "Invalid or Expired JWT", ex));
    }

    /** Hibernate Errors */
    @ExceptionHandler(HibernateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleHibernateExceptions(HibernateException ex) {
        return buildResponseEntity(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Database Error", ex));
    }

    public ResponseEntity<Object> buildResponseEntity(ApiError err) {
        return new ResponseEntity<>(err, err.getHttpStatus());
    }
}
