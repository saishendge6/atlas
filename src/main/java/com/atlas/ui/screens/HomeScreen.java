package com.atlas.ui.screens;

import com.atlas.config.AppConfig;
import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;
import com.atlas.util.AppTheme;

import java.util.Objects;

/**
 * Root screen of the shell.
 *
 * <p>Shows the ATLAS splash, the main menu and routes the user's choice to
 * the corresponding screen. Back at this level means exit.</p>
 */
public final class HomeScreen extends AbstractScreen {

    private final AppConfig config;

    public HomeScreen(AppConfig config) {
        super("Main Menu");
        this.config = Objects.requireNonNull(config, "config must not be null");
    }

    @Override
    protected void printHeader(OutputWriter output) {
        output.println();
        output.println(AppTheme.separator());
        output.println(AppTheme.center("ATLAS"));
        output.println(AppTheme.center(config.getTagline()));
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
            case "1" -> NavigationCommand.navigate(new DashboardScreen());
            case "2" -> NavigationCommand.navigate(new SubjectsScreen());
            case "3" -> NavigationCommand.navigate(new AttendanceScreen());
            case "4" -> NavigationCommand.navigate(new StudySessionsScreen());
            case "5" -> NavigationCommand.navigate(new AssignmentsScreen());
            case "6" -> NavigationCommand.navigate(new GoalsScreen());
            case "7" -> NavigationCommand.navigate(new ExpensesScreen());
            case "8" -> NavigationCommand.navigate(new SettingsScreen(config));
            case "9", "0" -> NavigationCommand.exit();
            default -> null;
        };
    }
}
