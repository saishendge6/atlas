package com.atlas.model;

import java.time.LocalDateTime;

/**
 * Marker contract for domain entities that are persisted by the repository
 * layer. Every entity carries a unique identifier, a creation timestamp and
 * the ability to copy itself with persistence fields assigned.
 *
 * @param <T> the concrete entity type (self-referential bound, so generic
 *            repository code can return the concrete type)
 */
public interface Identifiable<T extends Identifiable<T>> {

    /**
     * @return the unique identifier, or {@code null} for a not-yet-persisted draft
     */
    String getId();

    /**
     * @return when the entity was first persisted, or {@code null} for a draft
     */
    LocalDateTime getCreatedAt();

    /**
     * Copies this entity, assigning the persistence fields.
     *
     * @param id        the identifier assigned by the repository
     * @param createdAt the creation timestamp assigned by the repository
     * @return an equal entity carrying the persistence fields
     */
    T withPersistence(String id, LocalDateTime createdAt);
}
