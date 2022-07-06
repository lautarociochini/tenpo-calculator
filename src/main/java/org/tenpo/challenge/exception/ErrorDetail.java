package org.tenpo.challenge.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.tenpo.challenge.constants.ApiConstants;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ErrorDetail {

    private HttpStatus status;
    private String message;
    private List<String> errors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ApiConstants.DATE_TIME_DEFAULT_PATTERN)
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorDetail(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}