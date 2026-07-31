package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

/**
 * Dashboard screen (placeholder).
 *
 * <p>Will aggregate attendance, study sessions, assignments, goals and
 * expenses into a daily overview. For now it only explains its purpose.</p>
 */
public final class DashboardScreen extends AbstractScreen {

    public DashboardScreen() {
        super("Dashboard");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Your day at a glance will be shown here.");
        output.println("Attendance, study sessions, assignments, goals and expenses");
        output.println("will be summarised once data is available.");
        output.println("(Placeholder - evolves in a later version.)");
        return awaitBack(input, output);
    }
}
