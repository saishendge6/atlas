package com.atlas.ui.screens;

import com.atlas.model.Goal;
import com.atlas.model.GoalStatus;
import com.atlas.service.EntityService;
import com.atlas.service.GoalService;
import com.atlas.ui.InputReader;
import com.atlas.ui.InputPrompts;
import com.atlas.ui.OutputWriter;

/**
 * Manages goals: create, list, update, delete.
 */
public final class GoalsScreen extends EntityManagerScreen<Goal> {

    private final GoalService service;

    public GoalsScreen(GoalService service) {
        super("Goals");
        this.service = service;
    }

    @Override
    protected EntityService<Goal> service() {
        return service;
    }

    @Override
    protected String entityName() {
        return "goal";
    }

    @Override
    protected Goal createDraft(InputReader input, OutputWriter output) {
        String title = InputPrompts.readRequiredLine(input, output, "Title");
        var targetDate = InputPrompts.readDate(input, output, "Target date");
        GoalStatus status = promptStatus(null, input, output);
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (title == null || targetDate == null || status == null || notes == null) {
            return null;
        }
        return Goal.draft(title, targetDate, status, notes);
    }

    @Override
    protected Goal updateDraft(Goal current, InputReader input, OutputWriter output) {
        String title = InputPrompts.readRequiredLine(input, output, "Title");
        var targetDate = InputPrompts.readDate(input, output, "Target date");
        GoalStatus status = promptStatus(current.status(), input, output);
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (title == null || targetDate == null || status == null || notes == null) {
            return null;
        }
        String keptNotes = notes.isEmpty() ? current.notes() : notes;
        return new Goal(current.getId(), title, targetDate, status, keptNotes, current.createdAt());
    }

    @Override
    protected String describe(Goal goal) {
        String line = goal.title() + " - target " + goal.targetDate() + " [" + goal.status() + "]";
        if (goal.notes() != null && !goal.notes().isBlank()) {
            line += " - " + goal.notes();
        }
        return line;
    }

    private GoalStatus promptStatus(GoalStatus current, InputReader input, OutputWriter output) {
        GoalStatus[] statuses = GoalStatus.values();
        while (true) {
            output.println("Status:");
            for (int index = 0; index < statuses.length; index++) {
                String marker = statuses[index] == current ? " (current)" : "";
                output.println("  " + (index + 1) + ". " + statuses[index] + marker);
            }
            output.print("Select status (1-" + statuses.length + ") > ");
            String value = input.readLine();
            if (value == null) {
                return null;
            }
            try {
                int index = Integer.parseInt(value.trim());
                if (index >= 1 && index <= statuses.length) {
                    return statuses[index - 1];
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            output.println("Please enter a number between 1 and " + statuses.length + ".");
        }
    }
}
