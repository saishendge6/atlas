package com.atlas.service;

import com.atlas.exception.ValidationException;
import com.atlas.model.StudySession;
import com.atlas.model.Subject;
import com.atlas.repository.StudySessionRepository;
import com.atlas.repository.SubjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Business rules for study sessions.
 */
public final class StudySessionService implements EntityService<StudySession> {

    private final StudySessionRepository repository;
    private final SubjectRepository subjectRepository;

    public StudySessionService(StudySessionRepository repository, SubjectRepository subjectRepository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.subjectRepository = Objects.requireNonNull(subjectRepository, "subjectRepository must not be null");
    }

    @Override
    public List<StudySession> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<StudySession> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public StudySession create(StudySession session) {
        validate(session);
        return repository.create(session);
    }

    @Override
    public StudySession update(String id, StudySession session) {
        validate(session);
        return repository.update(id, session);
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
     * Removes all study sessions for the given subject.
     *
     * @param subjectId the subject whose sessions are removed
     */
    public void deleteBySubject(String subjectId) {
        repository.findAll().stream()
                .filter(session -> session.subjectId().equals(subjectId))
                .map(StudySession::getId)
                .forEach(repository::deleteById);
    }

    private void validate(StudySession session) {
        if (session == null || session.subjectId() == null || session.subjectId().isBlank()) {
            throw new ValidationException("A study session requires a subject.");
        }
        if (session.date() == null) {
            throw new ValidationException("A study session requires a date.");
        }
        if (session.durationMinutes() <= 0) {
            throw new ValidationException("Session duration must be greater than zero.");
        }
        requireSubject(session.subjectId());
    }

    private void requireSubject(String subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isEmpty()) {
            throw new ValidationException("No subject found with id " + subjectId + ".");
        }
    }
}
