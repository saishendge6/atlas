package com.atlas.repository;

import com.atlas.model.StudySession;

/**
 * JSON-backed repository for {@link StudySession} entities.
 */
public final class StudySessionRepository extends AbstractJsonRepository<StudySession> {

    public StudySessionRepository(JsonFileStore store) {
        super(store, StudySession.class, "study-sessions.json");
    }
}
