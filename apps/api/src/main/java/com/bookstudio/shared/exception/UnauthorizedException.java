package com.bookstudio.shared.exception;

/**
 * Credentials or tokens are missing, wrong or no longer valid. Mapped to 401.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
