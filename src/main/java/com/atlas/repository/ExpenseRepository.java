package com.atlas.repository;

import com.atlas.model.Expense;

/**
 * JSON-backed repository for {@link Expense} entities.
 */
public final class ExpenseRepository extends AbstractJsonRepository<Expense> {

    public ExpenseRepository(JsonFileStore store) {
        super(store, Expense.class, "expenses.json");
    }
}
