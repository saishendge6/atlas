package com.atlas.ui.screens;

import com.atlas.model.Expense;
import com.atlas.service.EntityService;
import com.atlas.service.ExpenseService;
import com.atlas.ui.InputReader;
import com.atlas.ui.InputPrompts;
import com.atlas.ui.OutputWriter;

/**
 * Manages expenses: create, list, update, delete.
 */
public final class ExpensesScreen extends EntityManagerScreen<Expense> {

    private final ExpenseService service;

    public ExpensesScreen(ExpenseService service) {
        super("Expenses");
        this.service = service;
    }

    @Override
    protected EntityService<Expense> service() {
        return service;
    }

    @Override
    protected String entityName() {
        return "expense";
    }

    @Override
    protected Expense createDraft(InputReader input, OutputWriter output) {
        String description = InputPrompts.readRequiredLine(input, output, "Description");
        var amount = InputPrompts.readPositiveAmount(input, output, "Amount");
        var date = InputPrompts.readDate(input, output, "Date");
        if (description == null || amount == null || date == null) {
            return null;
        }
        return Expense.draft(description, amount, date);
    }

    @Override
    protected Expense updateDraft(Expense current, InputReader input, OutputWriter output) {
        String description = InputPrompts.readRequiredLine(input, output, "Description");
        var amount = InputPrompts.readPositiveAmount(input, output, "Amount");
        var date = InputPrompts.readDate(input, output, "Date");
        if (description == null || amount == null || date == null) {
            return null;
        }
        return new Expense(current.getId(), description, amount, date, current.createdAt());
    }

    @Override
    protected String describe(Expense expense) {
        return expense.date() + " - " + formatAmount(expense.amount()) + " - " + expense.description();
    }

    private static String formatAmount(java.math.BigDecimal amount) {
        return amount.stripTrailingZeros().toPlainString();
    }
}
