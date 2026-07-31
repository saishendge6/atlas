package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

/**
 * Goals screen (placeholder).
 *
 * <p>Will let users set academic and personal goals and monitor progress.
 * For now it only explains its purpose.</p>
 */
public final class GoalsScreen extends AbstractScreen {

    public GoalsScreen() {
        super("Goals");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Set academic and personal goals here.");
        output.println("Progress tracking and streaks will be shown in this screen.");
        output.println("(Placeholder - evolves in a later version.)");
        return awaitBack(input, output);
    }
}
