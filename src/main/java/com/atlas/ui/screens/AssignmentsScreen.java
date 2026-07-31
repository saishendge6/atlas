package com.atlas.ui.screens;

import com.atlas.model.Assignment;
import com.atlas.model.AssignmentStatus;
import com.atlas.service.AssignmentService;
import com.atlas.service.EntityService;
import com.atlas.ui.InputReader;
import com.atlas.ui.InputPrompts;
import com.atlas.ui.OutputWriter;

import java.util.Optional;

/**
 * Manages assignments: create, list, update, delete.
 */
public final class AssignmentsScreen extends EntityManagerScreen<Assignment> {

    private final AssignmentService service;
    private final SubjectSelector subjectSelector;

    public AssignmentsScreen(AssignmentService service, SubjectSelector subjectSelector) {
        super("Assignments");
        this.service = service;
        this.subjectSelector = subjectSelector;
    }

    @Override
    protected EntityService<Assignment> service() {
        return service;
    }

    @Override
    protected String entityName() {
        return "assignment";
    }

    @Override
    protected Assignment createDraft(InputReader input, OutputWriter output) {
        Optional<com.atlas.model.Subject> subject = subjectSelector.select(input, output);
        if (subject.isEmpty()) {
            return null;
        }
        String title = InputPrompts.readRequiredLine(input, output, "Title");
        var dueDate = InputPrompts.readDate(input, output, "Due date");
        AssignmentStatus status = promptStatus(null, input, output);
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (title == null || dueDate == null || status == null || notes == null) {
            return null;
        }
        return Assignment.draft(subject.get().getId(), title, dueDate, status, notes);
    }

    @Override
    protected Assignment updateDraft(Assignment current, InputReader input, OutputWriter output) {
        Optional<com.atlas.model.Subject> subject = subjectSelector.select(input, output);
        if (subject.isEmpty()) {
            return null;
        }
        String title = InputPrompts.readRequiredLine(input, output, "Title");
        var dueDate = InputPrompts.readDate(input, output, "Due date");
        AssignmentStatus status = promptStatus(current.status(), input, output);
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (title == null || dueDate == null || status == null || notes == null) {
            return null;
        }
        String keptNotes = notes.isEmpty() ? current.notes() : notes;
        return new Assignment(current.getId(), subject.get().getId(), title, dueDate, status, keptNotes,
                current.createdAt());
    }

    @Override
    protected String describe(Assignment assignment) {
        String line = assignment.title() + " - due " + assignment.dueDate() + " [" + assignment.status() + "] - "
                + subjectSelector.name(assignment.subjectId());
        if (assignment.notes() != null && !assignment.notes().isBlank()) {
            line += " - " + assignment.notes();
        }
        return line;
    }

    private AssignmentStatus promptStatus(AssignmentStatus current, InputReader input, OutputWriter output) {
        AssignmentStatus[] statuses = AssignmentStatus.values();
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
