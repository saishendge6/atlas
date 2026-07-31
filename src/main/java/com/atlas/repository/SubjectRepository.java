package com.atlas.repository;

import com.atlas.model.Subject;

/**
 * JSON-backed repository for {@link Subject} entities.
 */
public final class SubjectRepository extends AbstractJsonRepository<Subject> {

    public SubjectRepository(JsonFileStore store) {
        super(store, Subject.class, "subjects.json");
    }
}
