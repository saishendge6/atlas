package com.atlas.service;

import com.atlas.exception.ValidationException;
import com.atlas.model.Goal;
import com.atlas.repository.GoalRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Business rules for goals.
 */
public final class GoalService implements EntityService<Goal> {

    private final GoalRepository repository;

    public GoalService(GoalRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
    }

    @Override
    public List<Goal> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Goal> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Goal create(Goal goal) {
        validate(goal);
        return repository.create(goal);
    }

    @Override
    public Goal update(String id, Goal goal) {
        validate(goal);
        return repository.update(id, goal);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    private static void validate(Goal goal) {
        if (goal == null || goal.title() == null || goal.title().isBlank()) {
            throw new ValidationException("Goal title is required.");
        }
        if (goal.targetDate() == null) {
            throw new ValidationException("A goal requires a target date.");
        }
        if (goal.status() == null) {
            throw new ValidationException("A goal requires a status.");
        }
    }
}
