package com.bookstudio.shared.exception;

/**
 * The request conflicts with the current state of the domain (e.g. lending a
 * copy that is already on loan). Mapped to 409 Conflict.
 */
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
