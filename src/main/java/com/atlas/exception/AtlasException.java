package com.atlas.exception;

/**
 * Base unchecked exception for all ATLAS-specific failures.
 *
 * <p>Using a dedicated base type keeps lower layers independent of framework
 * exceptions and gives callers a single catch type for application errors.</p>
 */
public class AtlasException extends RuntimeException {

    public AtlasException(String message) {
        super(message);
    }

    public AtlasException(String message, Throwable cause) {
        super(message, cause);
    }
}
