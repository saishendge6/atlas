package com.atlas;

import com.atlas.config.AppConfig;
import com.atlas.core.AtlasApplication;

/**
 * Entry point of the ATLAS command line application.
 *
 * <p>Kept deliberately thin: it only loads the configuration and delegates
 * to {@link AtlasApplication}, the application orchestrator.</p>
 */
public final class Main {

    private Main() {
        // Utility class: never instantiated.
    }

    public static void main(String[] args) {
        AppConfig config = AppConfig.load();
        AtlasApplication application = new AtlasApplication(config);
        application.run();
    }
}
