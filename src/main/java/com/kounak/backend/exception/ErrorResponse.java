package com.kounak.backend.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private String type;
    private String message;
    private String code;
    private LocalDateTime timestamp;
    private List<ValidationError> validationErrors;

    public ErrorResponse(String type, String message, String code) {
        this.type = type;
        this.message = message;
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String type, String message, String code, List<ValidationError> validationErrors) {
        this.type = type;
        this.message = message;
        this.code = code;
        this.timestamp = LocalDateTime.now();
        this.validationErrors = validationErrors;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
} 