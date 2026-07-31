package com.atlas.service;

import java.util.List;
import java.util.Optional;

/**
 * Business-facing contract for entity CRUD.
 *
 * <p>Services add validation and domain rules on top of the persistence
 * layer; the UI never talks to repositories directly.</p>
 *
 * @param <T> the entity type
 */
public interface EntityService<T> {

    /**
     * @return all entities, in insertion order
     */
    List<T> findAll();

    /**
     * Finds an entity by its identifier.
     */
    Optional<T> findById(String id);

    /**
     * Validates and persists a new entity.
     *
     * @param entity the draft to persist
     * @return the persisted entity
     */
    T create(T entity);

    /**
     * Validates and replaces the entity with the given id.
     *
     * @param id     the identifier of the entity to replace
     * @param entity the replacement entity
     * @return the updated entity
     */
    T update(String id, T entity);

    /**
     * Removes the entity with the given id.
     *
     * @param id the identifier of the entity to remove
     */
    void delete(String id);

    /**
     * @return the number of persisted entities
     */
    long count();
}
