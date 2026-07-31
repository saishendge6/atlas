package com.atlas.util;

/**
 * Shared visual constants and helpers for a consistent CLI look.
 *
 * <p>Centralising layout logic here keeps every screen's presentation
 * identical and easy to restyle.</p>
 */
public final class AppTheme {

    /** Width in characters of separators and centered banners. */
    public static final int LINE_WIDTH = 40;

    private AppTheme() {
        // Utility class: never instantiated.
    }

    /**
     * Horizontal rule used to frame screens.
     *
     * @return a separator of {@link #LINE_WIDTH} characters
     */
    public static String separator() {
        return "=".repeat(LINE_WIDTH);
    }

    /**
     * Centers the given text within {@link #LINE_WIDTH} characters.
     *
     * @param text the text to center
     * @return the padded text
     */
    public static String center(String text) {
        return center(text, LINE_WIDTH);
    }

    /**
     * Centers the given text within the given width.
     *
     * @param text  the text to center
     * @param width the total width of the result
     * @return the padded text, or the text unchanged when it is wider than
     *         the requested width
     */
    public static String center(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        return " ".repeat(leftPadding) + text + " ".repeat(totalPadding - leftPadding);
    }
}
