package com.atlas.model;

import java.time.LocalDateTime;

/**
 * A subject the student is enrolled in.
 *
 * @param id        unique identifier
 * @param name      display name, e.g. "Data Structures"
 * @param code      short course code, e.g. "CS201" (optional)
 * @param notes     free-form notes (optional)
 * @param createdAt creation timestamp
 */
public record Subject(String id, String name, String code, String notes, LocalDateTime createdAt) implements Identifiable<Subject> {

    /**
     * Creates an unpersisted draft from user-provided fields.
     */
    public static Subject draft(String name, String code, String notes) {
        return new Subject(null, name, code, notes, null);
    }

    /**
     * Copies this draft, assigning the persistence fields.
     */
    public Subject withPersistence(String id, LocalDateTime createdAt) {
        return new Subject(id, name, code, notes, createdAt);
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
