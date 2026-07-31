package com.atlas.ui;

/**
 * Contract for every navigable screen in the CLI shell.
 *
 * <p>Screens never touch {@link System#in} or {@link System#out} directly;
 * they render through {@link OutputWriter} and read through
 * {@link InputReader}. This keeps the presentation layer testable and
 * swappable (e.g. a web front end later).</p>
 */
public interface Screen {

    /**
     * Renders the screen and returns the command describing what happens next.
     *
     * @param input  the input source
     * @param output the output destination
     * @return the next navigation command; never null
     */
    NavigationCommand render(InputReader input, OutputWriter output);
}
