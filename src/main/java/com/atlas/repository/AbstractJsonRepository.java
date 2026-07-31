package com.atlas.repository;

import com.atlas.exception.NotFoundException;
import com.atlas.exception.ValidationException;
import com.atlas.model.Identifiable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Generic JSON-backed repository implementing the common lifecycle of an
 * entity collection: in-memory read of the backing file, then create /
 * update / delete with an immediate write-back.
 *
 * <p>Concrete repositories only supply their entity class and file name.</p>
 *
 * @param <T> the entity type
 */
public abstract class AbstractJsonRepository<T extends Identifiable<T>> implements Repository<T> {

    private final JsonFileStore store;
    private final Class<T> entityType;
    private final String fileName;

    protected AbstractJsonRepository(JsonFileStore store, Class<T> entityType, String fileName) {
        this.store = Objects.requireNonNull(store, "store must not be null");
        this.entityType = Objects.requireNonNull(entityType, "entityType must not be null");
        this.fileName = Objects.requireNonNull(fileName, "fileName must not be null");
    }

    @Override
    public List<T> findAll() {
        return store.readAll(entityType, fileName);
    }

    @Override
    public Optional<T> findById(String id) {
        return findAll().stream().filter(entity -> entity.getId().equals(id)).findFirst();
    }

    @Override
    public T create(T entity) {
        T persisted = entity.getId() == null
                ? entity.withPersistence(UUID.randomUUID().toString(), LocalDateTime.now())
                : entity;
        List<T> all = new ArrayList<>(findAll());
        if (all.stream().anyMatch(existing -> existing.getId().equals(persisted.getId()))) {
            throw new ValidationException("An entity with id " + persisted.getId() + " already exists.");
        }
        all.add(persisted);
        store.writeAll(all, fileName);
        return persisted;
    }

    @Override
    public T update(String id, T entity) {
        T replacement = entity.withPersistence(id, entity.getCreatedAt() == null ? LocalDateTime.now() : entity.getCreatedAt());
        List<T> all = new ArrayList<>(findAll());
        for (int index = 0; index < all.size(); index++) {
            if (all.get(index).getId().equals(id)) {
                all.set(index, replacement);
                store.writeAll(all, fileName);
                return replacement;
            }
        }
        throw new NotFoundException("No entity with id " + id + " was found.");
    }

    @Override
    public void deleteById(String id) {
        List<T> all = new ArrayList<>(findAll());
        if (!all.removeIf(entity -> entity.getId().equals(id))) {
            throw new NotFoundException("No entity with id " + id + " was found.");
        }
        store.writeAll(all, fileName);
    }

    @Override
    public long count() {
        return findAll().size();
    }
}
