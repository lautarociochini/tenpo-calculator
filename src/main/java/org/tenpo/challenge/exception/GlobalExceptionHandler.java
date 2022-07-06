package org.tenpo.challenge.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicatedUserException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleDuplicatedUserException(DuplicatedUserException ex) {
        ErrorDetail errorDetail = new ErrorDetail(BAD_REQUEST, ex.getMessage(), "An error occurred in user sign up");
        return buildResponseEntity(errorDetail);
    }

    @ExceptionHandler(InvalidSessionException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<Object> handleInvalidSessionIdException(InvalidSessionException ex) {
        ErrorDetail errorDetail = new ErrorDetail(UNAUTHORIZED, ex.getMessage(), "X-Auth-Token header should be valid");
        return buildResponseEntity(errorDetail);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorDetail errorDetail = new ErrorDetail(NOT_FOUND, ex.getMessage(), "provided credentials are invalid");
        return buildResponseEntity(errorDetail);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorDetail errorDetail = new ErrorDetail(NOT_FOUND, ex.getMessage(), "username not found");
        errorDetail.setMessage(ex.getMessage());
        return buildResponseEntity(errorDetail);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(errorDetail, new HttpHeaders(), errorDetail.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(Exception ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(BAD_REQUEST, ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorDetail, new HttpHeaders(), errorDetail.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        ErrorDetail errorDetail =
                new ErrorDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(errorDetail, new HttpHeaders(), errorDetail.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        ErrorDetail errorDetail = new ErrorDetail(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(errorDetail, new HttpHeaders(), errorDetail.getStatus());
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorDetail errorDetail) {
        return new ResponseEntity<>(errorDetail, errorDetail.getStatus());
    }

}