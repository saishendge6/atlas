package com.atlas.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Prompt helpers used by screens to collect typed input.
 *
 * <p>Every method loops until the input is valid, prints a helpful message
 * on failure, and returns {@code null} when the input stream ends
 * (Ctrl+D), allowing screens to abort gracefully.</p>
 */
public final class InputPrompts {

    private InputPrompts() {
        // Utility class: never instantiated.
    }

    /**
     * Reads a non-blank line.
     *
     * @param label the prompt text
     * @return the trimmed input, or {@code null} on end of stream
     */
    public static String readRequiredLine(InputReader input, OutputWriter output, String label) {
        while (true) {
            output.print(label + " > ");
            String value = input.readLine();
            if (value == null) {
                return null;
            }
            String trimmed = value.trim();
            if (!trimmed.isEmpty()) {
                return trimmed;
            }
            output.println("This field is required.");
        }
    }

    /**
     * Reads a line that may be left blank.
     *
     * @param label the prompt text
     * @return the trimmed input (possibly empty), or {@code null} on end
     *         of stream
     */
    public static String readOptionalLine(InputReader input, OutputWriter output, String label) {
        output.print(label + " > ");
        String value = input.readLine();
        return value == null ? null : value.trim();
    }

    /**
     * Reads a whole number greater than zero.
     *
     * @param label the prompt text
     * @return the value, or {@code null} on end of stream
     */
    public static Integer readPositiveInt(InputReader input, OutputWriter output, String label) {
        while (true) {
            output.print(label + " > ");
            String value = input.readLine();
            if (value == null) {
                return null;
            }
            try {
                int parsed = Integer.parseInt(value.trim());
                if (parsed > 0) {
                    return parsed;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            output.println("Please enter a whole number greater than zero.");
        }
    }

    /**
     * Reads a monetary amount greater than zero.
     *
     * @param label the prompt text
     * @return the amount, or {@code null} on end of stream
     */
    public static BigDecimal readPositiveAmount(InputReader input, OutputWriter output, String label) {
        while (true) {
            output.print(label + " > ");
            String value = input.readLine();
            if (value == null) {
                return null;
            }
            try {
                BigDecimal parsed = new BigDecimal(value.trim());
                if (parsed.compareTo(BigDecimal.ZERO) > 0) {
                    return parsed;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            output.println("Please enter an amount greater than zero, e.g. 499.50.");
        }
    }

    /**
     * Reads a date in yyyy-MM-dd format.
     *
     * @param label the prompt text
     * @return the date, or {@code null} on end of stream
     */
    public static LocalDate readDate(InputReader input, OutputWriter output, String label) {
        while (true) {
            output.print(label + " (yyyy-MM-dd) > ");
            String value = input.readLine();
            if (value == null) {
                return null;
            }
            try {
                return LocalDate.parse(value.trim());
            } catch (DateTimeParseException ignored) {
                output.println("Please enter a valid date, e.g. 2026-08-15.");
            }
        }
    }

    /**
     * Reads a yes/no confirmation.
     *
     * @param label the prompt text
     * @return the answer, or {@code null} on end of stream
     */
    public static Boolean readYesNo(InputReader input, OutputWriter output, String label) {
        while (true) {
            output.print(label + " (y/n) > ");
            String value = input.readLine();
            if (value == null) {
                return null;
            }
            switch (value.trim().toLowerCase()) {
                case "y", "yes" -> {
                    return Boolean.TRUE;
                }
                case "n", "no" -> {
                    return Boolean.FALSE;
                }
                default -> output.println("Please answer y or n.");
            }
        }
    }
}
