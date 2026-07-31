package com.atlas.ui.screens;

import com.atlas.model.Subject;
import com.atlas.service.SubjectService;
import com.atlas.ui.InputReader;
import com.atlas.ui.OutputWriter;

import java.util.List;
import java.util.Optional;

/**
 * Shared helper for screens that reference a subject: lists all subjects,
 * lets the user pick one by number, and resolves subject names for display.
 */
public final class SubjectSelector {

    private final SubjectService subjectService;

    public SubjectSelector(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Lists all subjects and prompts the user to pick one.
     *
     * @return the chosen subject, or empty when there are no subjects or
     *         the user ends the input stream
     */
    public Optional<Subject> select(InputReader input, OutputWriter output) {
        List<Subject> subjects = subjectService.findAll();
        output.println();
        if (subjects.isEmpty()) {
            output.println("Add a subject first (Subjects > Add subject).");
            return Optional.empty();
        }
        for (int index = 0; index < subjects.size(); index++) {
            output.println("  " + (index + 1) + ". " + subjects.get(index).name());
        }
        while (true) {
            output.print("Select a subject (1-" + subjects.size() + ") > ");
            String value = input.readLine();
            if (value == null) {
                return Optional.empty();
            }
            try {
                int index = Integer.parseInt(value.trim());
                if (index >= 1 && index <= subjects.size()) {
                    return Optional.of(subjects.get(index - 1));
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            output.println("Please enter a number between 1 and " + subjects.size() + ".");
        }
    }

    /**
     * Resolves a subject id to its display name.
     *
     * @param subjectId the subject identifier
     * @return the subject name, or "?" when the subject no longer exists
     */
    public String name(String subjectId) {
        return subjectService.findById(subjectId).map(Subject::name).orElse("?");
    }
}
