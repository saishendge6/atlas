package com.atlas.ui.screens;

import com.atlas.model.StudySession;
import com.atlas.service.EntityService;
import com.atlas.service.StudySessionService;
import com.atlas.ui.InputReader;
import com.atlas.ui.InputPrompts;
import com.atlas.ui.OutputWriter;

import java.util.Optional;

/**
 * Manages study sessions: create, list, update, delete.
 */
public final class StudySessionsScreen extends EntityManagerScreen<StudySession> {

    private final StudySessionService service;
    private final SubjectSelector subjectSelector;

    public StudySessionsScreen(StudySessionService service, SubjectSelector subjectSelector) {
        super("Study Sessions");
        this.service = service;
        this.subjectSelector = subjectSelector;
    }

    @Override
    protected EntityService<StudySession> service() {
        return service;
    }

    @Override
    protected String entityName() {
        return "study session";
    }

    @Override
    protected StudySession createDraft(InputReader input, OutputWriter output) {
        Optional<com.atlas.model.Subject> subject = subjectSelector.select(input, output);
        if (subject.isEmpty()) {
            return null;
        }
        var date = InputPrompts.readDate(input, output, "Date");
        Integer minutes = InputPrompts.readPositiveInt(input, output, "Duration (minutes)");
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (date == null || minutes == null || notes == null) {
            return null;
        }
        return StudySession.draft(subject.get().getId(), date, minutes, notes);
    }

    @Override
    protected StudySession updateDraft(StudySession current, InputReader input, OutputWriter output) {
        Optional<com.atlas.model.Subject> subject = subjectSelector.select(input, output);
        if (subject.isEmpty()) {
            return null;
        }
        var date = InputPrompts.readDate(input, output, "Date");
        Integer minutes = InputPrompts.readPositiveInt(input, output, "Duration (minutes)");
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (date == null || minutes == null || notes == null) {
            return null;
        }
        String keptNotes = notes.isEmpty() ? current.notes() : notes;
        return new StudySession(current.getId(), subject.get().getId(), date, minutes, keptNotes,
                current.createdAt());
    }

    @Override
    protected String describe(StudySession session) {
        String line = session.date() + " - " + session.durationMinutes() + " min - "
                + subjectSelector.name(session.subjectId());
        if (session.notes() != null && !session.notes().isBlank()) {
            line += " - " + session.notes();
        }
        return line;
    }
}
