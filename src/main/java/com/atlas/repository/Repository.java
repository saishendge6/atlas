package com.atlas.repository;

import com.atlas.model.Identifiable;

import java.util.List;
import java.util.Optional;

/**
 * Persistence contract for domain entities.
 *
 * <p>Implementations are free to store data however they like (JSON files
 * today, SQLite or a remote API later) - callers only ever depend on this
 * interface, keeping the service layer storage-agnostic.</p>
 *
 * @param <T> the entity type
 */
public interface Repository<T extends Identifiable<T>> {

    /**
     * @return all entities, in insertion order
     */
    List<T> findAll();

    /**
     * Finds an entity by its identifier.
     */
    Optional<T> findById(String id);

    /**
     * Persists a new entity, assigning its id and creation timestamp when
     * they are not yet set.
     *
     * @param entity the draft to persist
     * @return the persisted entity
     */
    T create(T entity);

    /**
     * Replaces the entity with the given id.
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
    void deleteById(String id);

    /**
     * @return the number of persisted entities
     */
    long count();
}
