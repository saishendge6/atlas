package com.atlas.service;

import com.atlas.exception.ValidationException;
import com.atlas.model.Expense;
import com.atlas.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Business rules for expenses.
 */
public final class ExpenseService implements EntityService<Expense> {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
    }

    @Override
    public List<Expense> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Expense> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Expense create(Expense expense) {
        validate(expense);
        return repository.create(expense);
    }

    @Override
    public Expense update(String id, Expense expense) {
        validate(expense);
        return repository.update(id, expense);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    /**
     * @return the total of all expenses, or zero when there are none
     */
    public BigDecimal totalSpent() {
        return repository.findAll().stream()
                .map(Expense::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void validate(Expense expense) {
        if (expense == null || expense.description() == null || expense.description().isBlank()) {
            throw new ValidationException("Expense description is required.");
        }
        if (expense.amount() == null || expense.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Expense amount must be greater than zero.");
        }
        if (expense.date() == null) {
            throw new ValidationException("An expense requires a date.");
        }
    }
}
