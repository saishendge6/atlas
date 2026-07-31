package com.atlas.service;

import com.atlas.exception.ValidationException;
import com.atlas.model.AttendanceRecord;
import com.atlas.model.Subject;
import com.atlas.repository.AttendanceRepository;
import com.atlas.repository.SubjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Business rules for attendance records.
 */
public final class AttendanceService implements EntityService<AttendanceRecord> {

    private final AttendanceRepository repository;
    private final SubjectRepository subjectRepository;

    public AttendanceService(AttendanceRepository repository, SubjectRepository subjectRepository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.subjectRepository = Objects.requireNonNull(subjectRepository, "subjectRepository must not be null");
    }

    @Override
    public List<AttendanceRecord> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AttendanceRecord> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public AttendanceRecord create(AttendanceRecord record) {
        validate(record);
        return repository.create(record);
    }

    @Override
    public AttendanceRecord update(String id, AttendanceRecord record) {
        validate(record);
        return repository.update(id, record);
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
     * Removes all attendance records for the given subject.
     *
     * @param subjectId the subject whose records are removed
     */
    public void deleteBySubject(String subjectId) {
        repository.findAll().stream()
                .filter(record -> record.subjectId().equals(subjectId))
                .map(AttendanceRecord::getId)
                .forEach(repository::deleteById);
    }

    private void validate(AttendanceRecord record) {
        if (record == null || record.subjectId() == null || record.subjectId().isBlank()) {
            throw new ValidationException("Attendance requires a subject.");
        }
        if (record.date() == null) {
            throw new ValidationException("Attendance requires a date.");
        }
        requireSubject(record.subjectId());
    }

    private void requireSubject(String subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isEmpty()) {
            throw new ValidationException("No subject found with id " + subjectId + ".");
        }
    }
}
