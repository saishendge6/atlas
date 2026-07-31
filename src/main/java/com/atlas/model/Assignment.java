package com.atlas.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * An assignment with a deadline and lifecycle status.
 *
 * @param id        unique identifier
 * @param subjectId the subject the assignment belongs to
 * @param title     assignment title
 * @param dueDate   submission deadline
 * @param status    current lifecycle status
 * @param notes     free-form notes (optional)
 * @param createdAt creation timestamp
 */
public record Assignment(String id, String subjectId, String title, LocalDate dueDate, AssignmentStatus status,
                         String notes, LocalDateTime createdAt) implements Identifiable<Assignment> {

    /**
     * Creates an unpersisted draft from user-provided fields.
     */
    public static Assignment draft(String subjectId, String title, LocalDate dueDate, AssignmentStatus status,
                                   String notes) {
        return new Assignment(null, subjectId, title, dueDate, status, notes, null);
    }

    /**
     * Copies this draft, assigning the persistence fields.
     */
    public Assignment withPersistence(String id, LocalDateTime createdAt) {
        return new Assignment(id, subjectId, title, dueDate, status, notes, createdAt);
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
