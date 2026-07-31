package com.atlas.ui.screens;

import com.atlas.config.AppConfig;
import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

import java.util.Objects;

/**
 * Settings screen (placeholder).
 *
 * <p>Shows application information from {@link AppConfig}; will later host
 * preferences such as themes, notifications and data exports.</p>
 */
public final class SettingsScreen extends AbstractScreen {

    private final AppConfig config;

    public SettingsScreen(AppConfig config) {
        super("Settings");
        this.config = Objects.requireNonNull(config, "config must not be null");
    }

    @Override
    protected NavigationCommand renderBody(InputReader input, OutputWriter output) {
        output.println();
        output.println("Application   : " + config.getAppName());
        output.println("Version       : " + config.getVersion());
        output.println("Data directory: " + config.getDataDirectory().toAbsolutePath().normalize());
        output.println("(Placeholder - preferences will be added here.)");
        return awaitBack(input, output);
    }
}
