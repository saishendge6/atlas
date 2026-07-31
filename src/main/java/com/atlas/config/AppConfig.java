package com.atlas.config;

import com.atlas.exception.AtlasException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Centralised application configuration.
 *
 * <p>Values are loaded from {@code application.properties} on the classpath.
 * When the resource or a key is missing, sensible defaults keep the
 * application functional. In a future web version this class can be replaced
 * by Spring's configuration mechanism without touching any other layer.</p>
 */
public final class AppConfig {

    private static final String PROPERTIES_RESOURCE = "/application.properties";
    private static final String DEFAULT_APP_NAME = "ATLAS";
    private static final String DEFAULT_VERSION = "1.0.0";
    private static final String DEFAULT_TAGLINE = "Your Academic Operating System";
    private static final String DEFAULT_DATA_DIRECTORY = "data";

    private final String appName;
    private final String version;
    private final String tagline;
    private final Path dataDirectory;

    private AppConfig(String appName, String version, String tagline, Path dataDirectory) {
        this.appName = appName;
        this.version = version;
        this.tagline = tagline;
        this.dataDirectory = dataDirectory;
    }

    /**
     * Loads the application configuration from the classpath resource.
     *
     * @return a fully populated configuration
     */
    public static AppConfig load() {
        Properties properties = loadProperties();
        return new AppConfig(
                properties.getProperty("app.name", DEFAULT_APP_NAME),
                properties.getProperty("app.version", DEFAULT_VERSION),
                properties.getProperty("app.tagline", DEFAULT_TAGLINE),
                Path.of(properties.getProperty("app.data-directory", DEFAULT_DATA_DIRECTORY))
        );
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = AppConfig.class.getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException exception) {
            throw new AtlasException("Failed to load application configuration.", exception);
        }
        return properties;
    }

    public String getAppName() {
        return appName;
    }

    public String getVersion() {
        return version;
    }

    public String getTagline() {
        return tagline;
    }

    /**
     * Directory where the JSON persistence layer will store its files.
     *
     * @return the configured data directory
     */
    public Path getDataDirectory() {
        return dataDirectory;
    }
}
