package com.bookstudio.shared.exception;

/**
 * The request is well-formed but asks for something the API does not support.
 * Mapped to 400 Bad Request.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
