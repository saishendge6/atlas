package com.atlas.ui.screens;

import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

/**
 * Expenses screen (placeholder).
 *
 * <p>Will record study-related expenses and keep a monthly budget. For now
 * it only explains its purpose.</p>
 */
public final class ExpensesScreen extends AbstractScreen {

    public ExpensesScreen() {
        super("Expenses");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Record study-related expenses here.");
        output.println("Monthly budgets and spending summaries will be shown.");
        output.println("(Placeholder - evolves in a later version.)");
        return awaitBack(input, output);
    }
}
