package com.atlas.exception;

/**
 * Thrown when user-provided data violates a business rule (e.g. a blank
 * required field or a reference to a non-existent subject).
 */
public class ValidationException extends AtlasException {

    public ValidationException(String message) {
        super(message);
    }
}
