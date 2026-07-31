package com.atlas.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * An academic or personal goal with a target date.
 *
 * @param id         unique identifier
 * @param title      goal title
 * @param targetDate when the goal should be reached
 * @param status     current lifecycle status
 * @param notes      free-form notes (optional)
 * @param createdAt  creation timestamp
 */
public record Goal(String id, String title, LocalDate targetDate, GoalStatus status, String notes,
                   LocalDateTime createdAt) implements Identifiable<Goal> {

    /**
     * Creates an unpersisted draft from user-provided fields.
     */
    public static Goal draft(String title, LocalDate targetDate, GoalStatus status, String notes) {
        return new Goal(null, title, targetDate, status, notes, null);
    }

    /**
     * Copies this draft, assigning the persistence fields.
     */
    public Goal withPersistence(String id, LocalDateTime createdAt) {
        return new Goal(id, title, targetDate, status, notes, createdAt);
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
