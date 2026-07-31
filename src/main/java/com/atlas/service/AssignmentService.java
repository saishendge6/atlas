package com.atlas.service;

import com.atlas.exception.ValidationException;
import com.atlas.model.Assignment;
import com.atlas.model.Subject;
import com.atlas.repository.AssignmentRepository;
import com.atlas.repository.SubjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Business rules for assignments.
 */
public final class AssignmentService implements EntityService<Assignment> {

    private final AssignmentRepository repository;
    private final SubjectRepository subjectRepository;

    public AssignmentService(AssignmentRepository repository, SubjectRepository subjectRepository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.subjectRepository = Objects.requireNonNull(subjectRepository, "subjectRepository must not be null");
    }

    @Override
    public List<Assignment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Assignment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Assignment create(Assignment assignment) {
        validate(assignment);
        return repository.create(assignment);
    }

    @Override
    public Assignment update(String id, Assignment assignment) {
        validate(assignment);
        return repository.update(id, assignment);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    /**
     * Removes all assignments for the given subject.
     *
     * @param subjectId the subject whose assignments are removed
     */
    public void deleteBySubject(String subjectId) {
        repository.findAll().stream()
                .filter(assignment -> assignment.subjectId().equals(subjectId))
                .map(Assignment::getId)
                .forEach(repository::deleteById);
    }

    private void validate(Assignment assignment) {
        if (assignment == null || assignment.title() == null || assignment.title().isBlank()) {
            throw new ValidationException("Assignment title is required.");
        }
        if (assignment.subjectId() == null || assignment.subjectId().isBlank()) {
            throw new ValidationException("An assignment requires a subject.");
        }
        if (assignment.dueDate() == null) {
            throw new ValidationException("An assignment requires a due date.");
        }
        if (assignment.status() == null) {
            throw new ValidationException("An assignment requires a status.");
        }
        requireSubject(assignment.subjectId());
    }

    private void requireSubject(String subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isEmpty()) {
            throw new ValidationException("No subject found with id " + subjectId + ".");
        }
    }
}
