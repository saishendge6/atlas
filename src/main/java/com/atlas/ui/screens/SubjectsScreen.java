package com.atlas.ui.screens;

import com.atlas.model.Subject;
import com.atlas.service.EntityService;
import com.atlas.service.SubjectService;
import com.atlas.ui.InputReader;
import com.atlas.ui.InputPrompts;
import com.atlas.ui.OutputWriter;

/**
 * Manages subjects: create, list, update, delete.
 */
public final class SubjectsScreen extends EntityManagerScreen<Subject> {

    private final SubjectService service;

    public SubjectsScreen(SubjectService service) {
        super("Subjects");
        this.service = service;
    }

    @Override
    protected EntityService<Subject> service() {
        return service;
    }

    @Override
    protected String entityName() {
        return "subject";
    }

    @Override
    protected Subject createDraft(InputReader input, OutputWriter output) {
        String name = InputPrompts.readRequiredLine(input, output, "Name");
        String code = InputPrompts.readOptionalLine(input, output, "Code (e.g. CS201)");
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (name == null || code == null || notes == null) {
            return null;
        }
        return Subject.draft(name, code, notes);
    }

    @Override
    protected Subject updateDraft(Subject current, InputReader input, OutputWriter output) {
        String name = InputPrompts.readRequiredLine(input, output, "Name");
        String code = InputPrompts.readOptionalLine(input, output, "Code (e.g. CS201)");
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (name == null || code == null || notes == null) {
            return null;
        }
        String keptCode = code.isEmpty() ? current.code() : code;
        String keptNotes = notes.isEmpty() ? current.notes() : notes;
        return new Subject(current.getId(), name, keptCode, keptNotes, current.createdAt());
    }

    @Override
    protected String describe(Subject subject) {
        StringBuilder line = new StringBuilder(subject.name());
        if (subject.code() != null && !subject.code().isBlank()) {
            line.append(" (").append(subject.code()).append(")");
        }
        if (subject.notes() != null && !subject.notes().isBlank()) {
            line.append(" - ").append(subject.notes());
        }
        return line.toString();
    }
}
