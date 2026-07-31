package com.atlas.service;

import com.atlas.exception.ValidationException;
import com.atlas.model.Subject;
import com.atlas.repository.SubjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Business rules for subjects.
 *
 * <p>Deleting a subject cascades to all of its dependent records
 * (attendance, study sessions, assignments).</p>
 */
public final class SubjectService implements EntityService<Subject> {

    private final SubjectRepository repository;
    private final AttendanceService attendanceService;
    private final StudySessionService studySessionService;
    private final AssignmentService assignmentService;

    public SubjectService(SubjectRepository repository,
                          AttendanceService attendanceService,
                          StudySessionService studySessionService,
                          AssignmentService assignmentService) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.attendanceService = Objects.requireNonNull(attendanceService, "attendanceService must not be null");
        this.studySessionService = Objects.requireNonNull(studySessionService, "studySessionService must not be null");
        this.assignmentService = Objects.requireNonNull(assignmentService, "assignmentService must not be null");
    }

    @Override
    public List<Subject> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Subject> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Subject create(Subject subject) {
        validate(subject);
        return repository.create(subject);
    }

    @Override
    public Subject update(String id, Subject subject) {
        validate(subject);
        return repository.update(id, subject);
    }

    @Override
    public void delete(String id) {
        attendanceService.deleteBySubject(id);
        studySessionService.deleteBySubject(id);
        assignmentService.deleteBySubject(id);
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    private static void validate(Subject subject) {
        if (subject == null || subject.name() == null || subject.name().isBlank()) {
            throw new ValidationException("Subject name is required.");
        }
    }
}
