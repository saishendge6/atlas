package com.atlas.repository;

import com.atlas.model.Assignment;

/**
 * JSON-backed repository for {@link Assignment} entities.
 */
public final class AssignmentRepository extends AbstractJsonRepository<Assignment> {

    public AssignmentRepository(JsonFileStore store) {
        super(store, Assignment.class, "assignments.json");
    }
}
