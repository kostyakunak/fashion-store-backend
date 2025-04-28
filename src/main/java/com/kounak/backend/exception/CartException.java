package com.kounak.backend.exception;

public class CartException extends RuntimeException {
    private final String code;
    private final String details;

    public CartException(String message, String code) {
        super(message);
        this.code = code;
        this.details = null;
    }

    public CartException(String message, String code, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }
} 