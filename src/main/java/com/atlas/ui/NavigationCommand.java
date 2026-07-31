package com.atlas.ui;

import com.atlas.exception.AtlasException;

import java.util.Objects;

/**
 * Immutable instruction produced by a {@link Screen} describing what the
 * navigation system should do next.
 *
 * <p>Screens never mutate navigation state themselves; they only return
 * commands, keeping the navigation flow centralised in
 * {@link NavigationController}.</p>
 */
public final class NavigationCommand {

    /**
     * The kinds of navigation a screen can request.
     */
    public enum Type {
        /** Terminate the application. */
        EXIT,
        /** Pop the current screen and return to the previous one. */
        BACK,
        /** Push the target screen onto the navigation stack. */
        NAVIGATE
    }

    private final Type type;
    private final Screen target;

    private NavigationCommand(Type type, Screen target) {
        this.type = type;
        this.target = target;
    }

    /**
     * Command that terminates the application.
     */
    public static NavigationCommand exit() {
        return new NavigationCommand(Type.EXIT, null);
    }

    /**
     * Command that returns to the previous screen.
     */
    public static NavigationCommand back() {
        return new NavigationCommand(Type.BACK, null);
    }

    /**
     * Command that navigates to the given screen.
     *
     * @param target the screen to open; must not be null
     */
    public static NavigationCommand navigate(Screen target) {
        if (target == null) {
            throw new AtlasException("Cannot navigate to a null screen.");
        }
        return new NavigationCommand(Type.NAVIGATE, Objects.requireNonNull(target));
    }

    public Type getType() {
        return type;
    }

    /**
     * The screen to open, only meaningful for {@link Type#NAVIGATE}.
     */
    public Screen getTarget() {
        return target;
    }
}
