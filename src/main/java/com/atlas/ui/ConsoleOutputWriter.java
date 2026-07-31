package com.atlas.ui;

/**
 * Default {@link OutputWriter} backed by {@code System.out}.
 */
public final class ConsoleOutputWriter implements OutputWriter {

    @Override
    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void println(String line) {
        System.out.println(line);
    }

    @Override
    public void println() {
        System.out.println();
    }
}
