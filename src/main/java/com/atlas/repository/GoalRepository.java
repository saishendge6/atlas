package com.atlas.repository;

import com.atlas.model.Goal;

/**
 * JSON-backed repository for {@link Goal} entities.
 */
public final class GoalRepository extends AbstractJsonRepository<Goal> {

    public GoalRepository(JsonFileStore store) {
        super(store, Goal.class, "goals.json");
    }
}
