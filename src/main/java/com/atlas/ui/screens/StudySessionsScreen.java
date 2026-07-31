package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

/**
 * Study sessions screen (placeholder).
 *
 * <p>Will plan, run and review focused study sessions with timers and logs.
 * For now it only explains its purpose.</p>
 */
public final class StudySessionsScreen extends AbstractScreen {

    public StudySessionsScreen() {
        super("Study Sessions");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Plan, run and review focused study sessions here.");
        output.println("Session timers and history logs will live in this screen.");
        output.println("(Placeholder - evolves in a later version.)");
        return awaitBack(input, output);
    }
}
