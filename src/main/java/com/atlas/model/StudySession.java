package com.atlas.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A logged study session for a subject.
 *
 * @param id              unique identifier
 * @param subjectId       the subject studied
 * @param date            the day of the session
 * @param durationMinutes length of the session in minutes
 * @param notes           free-form notes (optional)
 * @param createdAt       creation timestamp
 */
public record StudySession(String id, String subjectId, LocalDate date, int durationMinutes, String notes,
                           LocalDateTime createdAt) implements Identifiable<StudySession> {

    /**
     * Creates an unpersisted draft from user-provided fields.
     */
    public static StudySession draft(String subjectId, LocalDate date, int durationMinutes, String notes) {
        return new StudySession(null, subjectId, date, durationMinutes, notes, null);
    }

    /**
     * Copies this draft, assigning the persistence fields.
     */
    public StudySession withPersistence(String id, LocalDateTime createdAt) {
        return new StudySession(id, subjectId, date, durationMinutes, notes, createdAt);
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
