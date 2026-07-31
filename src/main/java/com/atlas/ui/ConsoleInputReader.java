package com.atlas.ui;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Default {@link InputReader} backed by {@code System.in}.
 *
 * <p>Handles end-of-stream gracefully: when the underlying stream closes
 * (Ctrl+D / Ctrl+Z), {@link #readLine()} returns {@code null} so the
 * navigation system can shut down cleanly instead of crashing.</p>
 */
public final class ConsoleInputReader implements InputReader {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException | IllegalStateException exception) {
            return null;
        }
    }
}
