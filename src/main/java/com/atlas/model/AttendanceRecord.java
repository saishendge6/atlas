package com.atlas.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A single attendance mark for a subject on a given date.
 *
 * @param id        unique identifier
 * @param subjectId the subject this record belongs to
 * @param date      the class date
 * @param present   whether the student was present
 * @param notes     free-form notes (optional)
 * @param createdAt creation timestamp
 */
public record AttendanceRecord(String id, String subjectId, LocalDate date, boolean present, String notes,
                               LocalDateTime createdAt) implements Identifiable<AttendanceRecord> {

    /**
     * Creates an unpersisted draft from user-provided fields.
     */
    public static AttendanceRecord draft(String subjectId, LocalDate date, boolean present, String notes) {
        return new AttendanceRecord(null, subjectId, date, present, notes, null);
    }

    /**
     * Copies this draft, assigning the persistence fields.
     */
    public AttendanceRecord withPersistence(String id, LocalDateTime createdAt) {
        return new AttendanceRecord(id, subjectId, date, present, notes, createdAt);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
