package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

/**
 * Attendance screen (placeholder).
 *
 * <p>Will track attendance per subject and surface trends. For now it only
 * explains its purpose.</p>
 */
public final class AttendanceScreen extends AbstractScreen {

    public AttendanceScreen() {
        super("Attendance");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Track attendance per subject here.");
        output.println("Weekly trends and percentage warnings will be shown.");
        output.println("(Placeholder - evolves in a later version.)");
        return awaitBack(input, output);
    }
}
