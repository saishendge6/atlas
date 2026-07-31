package com.atlas.service;

import com.atlas.exception.NotFoundException;
import com.atlas.exception.ValidationException;
import com.atlas.model.Assignment;
import com.atlas.model.AssignmentStatus;
import com.atlas.model.AttendanceRecord;
import com.atlas.model.StudySession;
import com.atlas.model.Subject;
import com.atlas.repository.AssignmentRepository;
import com.atlas.repository.AttendanceRepository;
import com.atlas.repository.ExpenseRepository;
import com.atlas.repository.GoalRepository;
import com.atlas.repository.JsonFileStore;
import com.atlas.repository.StudySessionRepository;
import com.atlas.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubjectServiceTest {

    @TempDir
    Path tempDir;

    private SubjectService subjectService;
    private AttendanceService attendanceService;
    private StudySessionService studySessionService;
    private AssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        JsonFileStore store = new JsonFileStore(tempDir);
        SubjectRepository subjectRepository = new SubjectRepository(store);
        AttendanceRepository attendanceRepository = new AttendanceRepository(store);
        StudySessionRepository studySessionRepository = new StudySessionRepository(store);
        AssignmentRepository assignmentRepository = new AssignmentRepository(store);

        attendanceService = new AttendanceService(attendanceRepository, subjectRepository);
        studySessionService = new StudySessionService(studySessionRepository, subjectRepository);
        assignmentService = new AssignmentService(assignmentRepository, subjectRepository);
        subjectService = new SubjectService(subjectRepository, attendanceService, studySessionService, assignmentService);
    }

    @Test
    void createAssignsIdAndCreatedAt() {
        Subject created = subjectService.create(Subject.draft("Data Structures", "CS201", null));

        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertEquals(1, subjectService.count());
    }

    @Test
    void createRejectsBlankName() {
        assertThrows(ValidationException.class,
                () -> subjectService.create(Subject.draft("   ", "CS201", null)));
    }

    @Test
    void updateMissingSubjectThrowsNotFound() {
        assertThrows(NotFoundException.class,
                () -> subjectService.update("missing", Subject.draft("Databases", "CS301", null)));
    }

    @Test
    void updateReplacesEntityInPlace() {
        Subject created = subjectService.create(Subject.draft("Data Structures", "CS201", null));

        subjectService.update(created.getId(), Subject.draft("Advanced Data Structures", "CS201", "New notes"));

        assertEquals(1, subjectService.count());
        assertEquals("Advanced Data Structures", subjectService.findAll().get(0).name());
        assertEquals(created.getId(), subjectService.findAll().get(0).getId());
    }

    @Test
    void deleteCascadesToDependentRecords() {
        Subject subject = subjectService.create(Subject.draft("Data Structures", "CS201", null));
        attendanceService.create(AttendanceRecord.draft(subject.getId(), LocalDate.now(), true, null));
        studySessionService.create(StudySession.draft(subject.getId(), LocalDate.now(), 90, null));
        assignmentService.create(Assignment.draft(subject.getId(), "Assignment 1", LocalDate.now().plusDays(7),
                AssignmentStatus.NOT_STARTED, null));

        subjectService.delete(subject.getId());

        assertEquals(0, subjectService.count());
        assertEquals(0, attendanceService.count());
        assertEquals(0, studySessionService.count());
        assertEquals(0, assignmentService.count());
    }

    @Test
    void recordsRequireAnExistingSubject() {
        assertThrows(ValidationException.class,
                () -> attendanceService.create(AttendanceRecord.draft("nope", LocalDate.now(), true, null)));
    }
}
