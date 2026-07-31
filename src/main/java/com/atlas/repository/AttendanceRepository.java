package com.atlas.repository;

import com.atlas.model.AttendanceRecord;

/**
 * JSON-backed repository for {@link AttendanceRecord} entities.
 */
public final class AttendanceRepository extends AbstractJsonRepository<AttendanceRecord> {

    public AttendanceRepository(JsonFileStore store) {
        super(store, AttendanceRecord.class, "attendance.json");
    }
}
