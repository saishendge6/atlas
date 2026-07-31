package com.atlas.ui.screens;

import com.atlas.core.ApplicationContext;
import com.atlas.model.Assignment;
import com.atlas.model.AssignmentStatus;
import com.atlas.model.Goal;
import com.atlas.model.GoalStatus;
import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

import java.math.BigDecimal;

/**
 * Daily overview screen.
 *
 * <p>Aggregates the current state of every module so the student sees the
 * whole academic picture at a glance.</p>
 */
public final class DashboardScreen extends AbstractScreen {

    private final ApplicationContext context;

    public DashboardScreen(ApplicationContext context) {
        super("Dashboard");
        this.context = context;
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Subjects        : " + context.getSubjectService().count());
        output.println("Assignments     : " + context.getAssignmentService().count()
                + " (" + pendingAssignments() + " pending)");
        output.println("Study sessions  : " + context.getStudySessionService().count());
        output.println("Attendance      : " + context.getAttendanceService().count() + " records");
        output.println("Goals           : " + context.getGoalService().count()
                + " (" + activeGoals() + " active)");
        output.println("Expenses        : " + formatAmount(context.getExpenseService().totalSpent()) + " total");
        return awaitBack(input, output);
    }

    private long pendingAssignments() {
        return context.getAssignmentService().findAll().stream()
                .filter(assignment -> assignment.status() != AssignmentStatus.SUBMITTED)
                .count();
    }

    private long activeGoals() {
        return context.getGoalService().findAll().stream()
                .filter(goal -> goal.status() == GoalStatus.ACTIVE)
                .count();
    }

    private static String formatAmount(BigDecimal amount) {
        return amount.stripTrailingZeros().toPlainString();
    }
}
