package com.atlas.core;

import com.atlas.config.AppConfig;
import com.atlas.repository.AssignmentRepository;
import com.atlas.repository.AttendanceRepository;
import com.atlas.repository.ExpenseRepository;
import com.atlas.repository.GoalRepository;
import com.atlas.repository.JsonFileStore;
import com.atlas.repository.StudySessionRepository;
import com.atlas.repository.SubjectRepository;
import com.atlas.service.AssignmentService;
import com.atlas.service.AttendanceService;
import com.atlas.service.ExpenseService;
import com.atlas.service.GoalService;
import com.atlas.service.StudySessionService;
import com.atlas.service.SubjectService;

import java.util.Objects;

/**
 * Composition root holding every application dependency.
 *
 * <p>Constructs the persistence store, repositories and services in the
 * correct order so that all dependencies can be injected via constructors.
 * Screens receive only what they need through this context.</p>
 */
public final class ApplicationContext {

    private final AppConfig config;

    private final SubjectRepository subjectRepository;
    private final AttendanceRepository attendanceRepository;
    private final StudySessionRepository studySessionRepository;
    private final AssignmentRepository assignmentRepository;
    private final GoalRepository goalRepository;
    private final ExpenseRepository expenseRepository;

    private final SubjectService subjectService;
    private final AttendanceService attendanceService;
    private final StudySessionService studySessionService;
    private final AssignmentService assignmentService;
    private final GoalService goalService;
    private final ExpenseService expenseService;

    public ApplicationContext(AppConfig config) {
        this.config = Objects.requireNonNull(config, "config must not be null");

        JsonFileStore fileStore = new JsonFileStore(config.getDataDirectory());

        this.subjectRepository = new SubjectRepository(fileStore);
        this.attendanceRepository = new AttendanceRepository(fileStore);
        this.studySessionRepository = new StudySessionRepository(fileStore);
        this.assignmentRepository = new AssignmentRepository(fileStore);
        this.goalRepository = new GoalRepository(fileStore);
        this.expenseRepository = new ExpenseRepository(fileStore);

        this.attendanceService = new AttendanceService(attendanceRepository, subjectRepository);
        this.studySessionService = new StudySessionService(studySessionRepository, subjectRepository);
        this.assignmentService = new AssignmentService(assignmentRepository, subjectRepository);
        this.goalService = new GoalService(goalRepository);
        this.expenseService = new ExpenseService(expenseRepository);
        this.subjectService = new SubjectService(subjectRepository, attendanceService, studySessionService, assignmentService);
    }

    public AppConfig getConfig() {
        return config;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public AttendanceService getAttendanceService() {
        return attendanceService;
    }

    public StudySessionService getStudySessionService() {
        return studySessionService;
    }

    public AssignmentService getAssignmentService() {
        return assignmentService;
    }

    public GoalService getGoalService() {
        return goalService;
    }

    public ExpenseService getExpenseService() {
        return expenseService;
    }
}
