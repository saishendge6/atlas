package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;
import com.atlas.ui.Screen;
import com.atlas.util.AppTheme;

import java.util.Objects;

/**
 * Base template for every screen in the shell.
 *
 * <p>Handles the standard header rendering (separator, centered title,
 * separator) and the shared "0. Back" loop, so concrete screens only
 * implement their own body via {@link #renderBody(InputReader, OutputWriter)}.
 * The {@code render} method is final to enforce the common layout.</p>
 */
public abstract class AbstractScreen implements Screen {

    private final String title;

    protected AbstractScreen(String title) {
        this.title = Objects.requireNonNull(title, "title must not be null");
    }

    @Override
    public final NavigationCommand render(InputReader input, OutputWriter output) {
        printHeader(output);
        return renderBody(input, output);
    }

    /**
     * Renders the standard screen header. Override for custom banners
     * (e.g. the home screen splash).
     */
    protected void printHeader(OutputWriter output) {
        output.println();
        output.println(AppTheme.separator());
        output.println(AppTheme.center(title));
        output.println(AppTheme.separator());
    }

    /**
     * Renders the screen's own content.
     *
     * @param input  the input source
     * @param output the output destination
     * @return the next navigation command
     */
    protected abstract NavigationCommand renderBody(InputReader input, OutputWriter output);

    /**
     * Standard interaction loop for screens with no actions yet:
     * shows the Back option and loops until the user confirms,
     * rejecting invalid input along the way.
     *
     * @param input  the input source
     * @param output the output destination
     * @return {@link NavigationCommand#back()} on confirmation, or
     *         {@link NavigationCommand#exit()} when the input stream ends
     */
    protected final NavigationCommand awaitBack(InputReader input, OutputWriter output) {
        output.println();
        output.println("0. Back");
        while (true) {
            output.print("> ");
            String choice = input.readLine();
            if (choice == null) {
                return NavigationCommand.exit();
            }
            if ("0".equals(choice.trim())) {
                return NavigationCommand.back();
            }
            output.println("Invalid option. Press 0 to go back.");
        }
    }
}
