package com.atlas.core;

import com.atlas.config.AppConfig;
import com.atlas.ui.ConsoleInputReader;
import com.atlas.ui.ConsoleOutputWriter;
import com.atlas.ui.InputReader;
import com.atlas.ui.NavigationController;
import com.atlas.ui.OutputWriter;
import com.atlas.ui.screens.HomeScreen;

import java.util.Objects;

/**
 * Application orchestrator.
 *
 * <p>Wires together the configuration, the input/output adapters and the
 * navigation system, then starts the CLI shell. This is the composition root
 * of the application; no presentation or persistence logic lives here.</p>
 */
public final class AtlasApplication {

    private final AppConfig config;

    public AtlasApplication(AppConfig config) {
        this.config = Objects.requireNonNull(config, "config must not be null");
    }

    /**
     * Starts the interactive CLI shell and blocks until the user exits.
     */
    public void run() {
        InputReader input = new ConsoleInputReader();
        OutputWriter output = new ConsoleOutputWriter();

        NavigationController navigation = new NavigationController();
        navigation.run(new HomeScreen(config), input, output);

        output.println();
        output.println("Goodbye. Keep building yourself.");
    }
}
