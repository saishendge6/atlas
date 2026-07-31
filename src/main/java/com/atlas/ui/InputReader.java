package com.atlas.ui;

/**
 * Abstraction over line-based text input.
 *
 * <p>Screens depend on this interface instead of {@code System.in} so the
 * CLI shell can be tested and later replaced by another front end.</p>
 */
public interface InputReader {

    /**
     * Reads the next line of input.
     *
     * @return the raw line, or {@code null} when the input stream has ended
     *         (e.g. the user pressed Ctrl+D)
     */
    String readLine();
}
