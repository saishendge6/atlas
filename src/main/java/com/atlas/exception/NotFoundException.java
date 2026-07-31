package com.atlas.exception;

/**
 * Thrown when an entity expected to exist cannot be found by its identifier.
 */
public class NotFoundException extends AtlasException {

    public NotFoundException(String message) {
        super(message);
    }
}
