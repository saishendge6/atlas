package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

/**
 * Assignments screen (placeholder).
 *
 * <p>Will manage assignments, deadlines and submission statuses. For now it
 * only explains its purpose.</p>
 */
public final class AssignmentsScreen extends AbstractScreen {

    public AssignmentsScreen() {
        super("Assignments");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Manage assignments, deadlines and submission status here.");
        output.println("Upcoming and overdue assignments will be listed in this screen.");
        output.println("(Placeholder - evolves in a later version.)");
        return awaitBack(input, output);
    }
}
