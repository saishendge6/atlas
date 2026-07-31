package com.atlas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A study-related expense entry.
 *
 * @param id          unique identifier
 * @param description what the money was spent on
 * @param amount      the amount paid, always positive
 * @param date        the day of the expense
 * @param createdAt   creation timestamp
 */
public record Expense(String id, String description, BigDecimal amount, LocalDate date,
                      LocalDateTime createdAt) implements Identifiable<Expense> {

    /**
     * Creates an unpersisted draft from user-provided fields.
     */
    public static Expense draft(String description, BigDecimal amount, LocalDate date) {
        return new Expense(null, description, amount, date, null);
    }

    /**
     * Copies this draft, assigning the persistence fields.
     */
    public Expense withPersistence(String id, LocalDateTime createdAt) {
        return new Expense(id, description, amount, date, createdAt);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
