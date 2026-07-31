package com.atlas.ui;

/**
 * Abstraction over text output.
 *
 * <p>Screens depend on this interface instead of {@code System.out} so the
 * presentation layer stays decoupled from the console.</p>
 */
public interface OutputWriter {

    /**
     * Prints text without a trailing line break.
     *
     * @param text the text to print
     */
    void print(String text);

    /**
     * Prints a line followed by a line break.
     *
     * @param line the line to print
     */
    void println(String line);

    /**
     * Prints a blank line.
     */
    void println();
}
