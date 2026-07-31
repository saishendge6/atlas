package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

/**
 * Subjects screen (placeholder).
 *
 * <p>Will manage subjects, their timetables and associated study material.
 * For now it only explains its purpose.</p>
 */
public final class SubjectsScreen extends AbstractScreen {

    public SubjectsScreen() {
        super("Subjects");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Add, view and manage your subjects here.");
        output.println("Timetables and study material will be linked per subject.");
        output.println("(Placeholder - evolves in a later version.)");
        return awaitBack(input, output);
    }
}
