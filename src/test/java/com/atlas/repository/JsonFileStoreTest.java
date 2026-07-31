package com.atlas.repository;

import com.atlas.exception.AtlasException;
import com.atlas.model.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonFileStoreTest {

    @TempDir
    Path tempDir;

    @Test
    void readAllReturnsEmptyWhenFileDoesNotExist() {
        JsonFileStore store = new JsonFileStore(tempDir);
        assertTrue(store.readAll(Subject.class, "subjects.json").isEmpty());
    }

    @Test
    void writeThenReadRoundTripsEntities() {
        JsonFileStore store = new JsonFileStore(tempDir);
        List<Subject> subjects = List.of(
                new Subject("s1", "Data Structures", "CS201", "Trees", LocalDateTime.of(2026, 7, 1, 10, 0)),
                new Subject("s2", "Databases", "CS301", null, LocalDateTime.of(2026, 7, 2, 11, 30))
        );

        store.writeAll(subjects, "subjects.json");
        List<Subject> loaded = store.readAll(Subject.class, "subjects.json");

        assertEquals(subjects, loaded);
        assertTrue(Files.exists(tempDir.resolve("subjects.json")));
    }

    @Test
    void corruptFileThrowsAtlasException() throws Exception {
        Files.writeString(tempDir.resolve("subjects.json"), "not valid json");
        JsonFileStore store = new JsonFileStore(tempDir);

        assertThrows(AtlasException.class, () -> store.readAll(Subject.class, "subjects.json"));
    }
}
