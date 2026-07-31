package com.atlas.ui.screens;

import com.atlas.core.ApplicationContext;
import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;
import com.atlas.util.AppTheme;

/**
 * Root screen of the shell.
 *
 * <p>Shows the ATLAS splash, the main menu and routes the user's choice to
 * the corresponding screen. Back at this level means exit.</p>
 */
public final class HomeScreen extends AbstractScreen {

    private final ApplicationContext context;

    public HomeScreen(ApplicationContext context) {
        super("Main Menu");
        this.context = context;
    }

    @Override
    protected void printHeader(OutputWriter output) {
        output.println();
        output.println(AppTheme.separator());
        output.println(AppTheme.center("ATLAS"));
        output.println(AppTheme.center(context.getConfig().getTagline()));
        output.println(AppTheme.separator());
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        printMenu(output);
        while (true) {
            output.print("> ");
            String choice = input.readLine();
            if (choice == null) {
                return NavigationCommand.exit();
            }
            NavigationCommand command = route(choice.trim());
            if (command != null) {
                return command;
            }
            output.println("Invalid option. Please enter a number between 1 and 9.");
        }
    }

    private void printMenu(OutputWriter output) {
        output.println("1. Dashboard");
        output.println("2. Subjects");
        output.println("3. Attendance");
        output.println("4. Study Sessions");
        output.println("5. Assignments");
        output.println("6. Goals");
        output.println("7. Expenses");
        output.println("8. Settings");
        output.println("9. Exit");
    }

    /**
     * Maps a raw menu choice to a navigation command.
     *
     * @param choice the trimmed user input
     * @return the matching command, or {@code null} when the input is invalid
     */
    private NavigationCommand route(String choice) {
        return switch (choice) {
            case "1" -> NavigationCommand.navigate(new DashboardScreen(context));
            case "2" -> NavigationCommand.navigate(new SubjectsScreen(context.getSubjectService()));
            case "3" -> NavigationCommand.navigate(
                    new AttendanceScreen(context.getAttendanceService(), new SubjectSelector(context.getSubjectService())));
            case "4" -> NavigationCommand.navigate(
                    new StudySessionsScreen(context.getStudySessionService(), new SubjectSelector(context.getSubjectService())));
            case "5" -> NavigationCommand.navigate(
                    new AssignmentsScreen(context.getAssignmentService(), new SubjectSelector(context.getSubjectService())));
            case "6" -> NavigationCommand.navigate(new GoalsScreen(context.getGoalService()));
            case "7" -> NavigationCommand.navigate(new ExpensesScreen(context.getExpenseService()));
            case "8" -> NavigationCommand.navigate(new SettingsScreen(context.getConfig()));
            case "9", "0" -> NavigationCommand.exit();
            default -> null;
        };
    }
}
