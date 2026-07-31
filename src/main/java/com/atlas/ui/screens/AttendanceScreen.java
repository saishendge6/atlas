package com.atlas.ui.screens;

import com.atlas.model.AttendanceRecord;
import com.atlas.service.AttendanceService;
import com.atlas.service.EntityService;
import com.atlas.ui.InputReader;
import com.atlas.ui.InputPrompts;
import com.atlas.ui.OutputWriter;

import java.util.Optional;

/**
 * Manages attendance records: create, list, update, delete.
 */
public final class AttendanceScreen extends EntityManagerScreen<AttendanceRecord> {

    private final AttendanceService service;
    private final SubjectSelector subjectSelector;

    public AttendanceScreen(AttendanceService service, SubjectSelector subjectSelector) {
        super("Attendance");
        this.service = service;
        this.subjectSelector = subjectSelector;
    }

    @Override
    protected EntityService<AttendanceRecord> service() {
        return service;
    }

    @Override
    protected String entityName() {
        return "attendance record";
    }

    @Override
    protected AttendanceRecord createDraft(InputReader input, OutputWriter output) {
        Optional<com.atlas.model.Subject> subject = subjectSelector.select(input, output);
        if (subject.isEmpty()) {
            return null;
        }
        var date = InputPrompts.readDate(input, output, "Date");
        Boolean present = InputPrompts.readYesNo(input, output, "Present");
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (date == null || present == null || notes == null) {
            return null;
        }
        return AttendanceRecord.draft(subject.get().getId(), date, present, notes);
    }

    @Override
    protected AttendanceRecord updateDraft(AttendanceRecord current, InputReader input, OutputWriter output) {
        Optional<com.atlas.model.Subject> subject = subjectSelector.select(input, output);
        if (subject.isEmpty()) {
            return null;
        }
        var date = InputPrompts.readDate(input, output, "Date");
        Boolean present = InputPrompts.readYesNo(input, output, "Present");
        String notes = InputPrompts.readOptionalLine(input, output, "Notes");
        if (date == null || present == null || notes == null) {
            return null;
        }
        String keptNotes = notes.isEmpty() ? current.notes() : notes;
        return new AttendanceRecord(current.getId(), subject.get().getId(), date, present, keptNotes,
                current.createdAt());
    }

    @Override
    protected String describe(AttendanceRecord record) {
        String state = record.present() ? "Present" : "Absent";
        String line = record.date() + " - " + state + " - " + subjectSelector.name(record.subjectId());
        if (record.notes() != null && !record.notes().isBlank()) {
            line += " - " + record.notes();
        }
        return line;
    }
}
