package com.atlas.ui;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Stack-based navigation system for the CLI shell.
 *
 * <p>Holds the navigation stack of screens and runs the render loop:
 * the screen on top of the stack renders itself, then its returned
 * {@link NavigationCommand} decides whether to push, pop or exit.
 * This structure supports arbitrary nesting of sub-screens.</p>
 */
public final class NavigationController {

    private final Deque<Screen> navigationStack = new ArrayDeque<>();

    /**
     * Runs the navigation loop until the user exits the application.
     *
     * @param startScreen the first screen to show; must not be null
     * @param input       the input source shared by all screens
     * @param output      the output destination shared by all screens
     */
    public void run(Screen startScreen, InputReader input, OutputWriter output) {
        Objects.requireNonNull(startScreen, "startScreen must not be null");
        Objects.requireNonNull(input, "input must not be null");
        Objects.requireNonNull(output, "output must not be null");

        navigationStack.push(startScreen);
        while (!navigationStack.isEmpty()) {
            Screen current = navigationStack.peek();
            NavigationCommand command = current.render(input, output);
            switch (command.getType()) {
                case NAVIGATE -> navigationStack.push(command.getTarget());
                case BACK -> navigationStack.pop();
                case EXIT -> {
                    return;
                }
            }
        }
    }
}
