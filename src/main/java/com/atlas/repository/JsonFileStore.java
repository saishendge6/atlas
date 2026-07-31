package com.atlas.repository;

import com.atlas.exception.AtlasException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Low-level JSON file storage.
 *
 * <p>Reads and writes collections of entities as pretty-printed JSON files
 * inside the configured data directory. The directory is created on demand.
 * This class knows nothing about business rules; it is the single place
 * where the JSON file format lives.</p>
 */
public final class JsonFileStore {

    private final Path dataDirectory;
    private final JsonMapper objectMapper;

    /**
     * @param dataDirectory directory where JSON files are stored; created
     *                      lazily on first write
     */
    public JsonFileStore(Path dataDirectory) {
        this.dataDirectory = Objects.requireNonNull(dataDirectory, "dataDirectory must not be null");
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    /**
     * Reads all entities of the given type from the given file.
     *
     * @param type     the entity class
     * @param fileName the file name inside the data directory
     * @return the stored entities, or an empty list when the file does not
     *         exist yet
     */
    public <T> List<T> readAll(Class<T> type, String fileName) {
        Path file = file(fileName);
        if (!Files.exists(file)) {
            return List.of();
        }
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
            return objectMapper.readValue(file.toFile(), listType);
        } catch (IOException exception) {
            throw new AtlasException("Failed to read data file: " + fileName, exception);
        }
    }

    /**
     * Writes all items to the given file, replacing its previous content.
     *
     * @param items    the items to persist
     * @param fileName the file name inside the data directory
     */
    public <T> void writeAll(Collection<T> items, String fileName) {
        try {
            Files.createDirectories(dataDirectory);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file(fileName).toFile(), items);
        } catch (IOException exception) {
            throw new AtlasException("Failed to write data file: " + fileName, exception);
        }
    }

    private Path file(String fileName) {
        return dataDirectory.resolve(fileName);
    }
}
