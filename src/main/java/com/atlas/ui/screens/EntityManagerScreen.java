package com.atlas.ui.screens;

import com.atlas.exception.AtlasException;
import com.atlas.model.Identifiable;
import com.atlas.service.EntityService;
import com.atlas.ui.InputReader;
import com.atlas.ui.InputPrompts;
import com.atlas.ui.NavigationCommand;
import com.atlas.ui.OutputWriter;

import java.util.List;
import java.util.Optional;

/**
 * Generic screen managing the CRUD lifecycle of one entity type through a
 * numbered sub-menu: list, add, update, delete, back.
 *
 * <p>Concrete screens provide the entity service, the singular/plural name
 * of the entity, how it is described, and the input forms for create and
 * update. All error handling (validation, not-found) is centralised here:
 * domain messages are shown to the user and the menu stays alive.</p>
 *
 * @param <T> the entity type
 */
public abstract class EntityManagerScreen<T extends Identifiable<?>> extends AbstractScreen {

    protected EntityManagerScreen(String title) {
        super(title);
    }

    @Override
    protected final NavigationCommand renderBody(InputReader input, OutputWriter output) {
        while (true) {
            output.println();
            printSubMenu(output);
            output.print("> ");
            String choice = input.readLine();
            if (choice == null) {
                return NavigationCommand.exit();
            }
            switch (choice.trim()) {
                case "1" -> list(input, output);
                case "2" -> add(input, output);
                case "3" -> update(input, output);
                case "4" -> delete(input, output);
                case "5", "0" -> {
                    return NavigationCommand.back();
                }
                default -> output.println("Invalid option. Please enter a number between 1 and 5.");
            }
        }
    }

    private void printSubMenu(OutputWriter output) {
        output.println("1. List " + entityName() + "s");
        output.println("2. Add " + entityName());
        output.println("3. Update " + entityName());
        output.println("4. Delete " + entityName());
        output.println("5. Back");
    }

    private void list(InputReader input, OutputWriter output) {
        List<T> all = service().findAll();
        output.println();
        if (all.isEmpty()) {
            output.println("No " + entityName() + "s yet.");
            return;
        }
        for (int index = 0; index < all.size(); index++) {
            output.println("  " + (index + 1) + ". " + describe(all.get(index)));
        }
    }

    private void add(InputReader input, OutputWriter output) {
        T draft = createDraft(input, output);
        if (draft == null) {
            return;
        }
        try {
            T created = service().create(draft);
            output.println("Added " + entityName() + ": " + describe(created));
        } catch (AtlasException exception) {
            output.println(exception.getMessage());
        }
    }

    private void update(InputReader input, OutputWriter output) {
        Optional<T> selected = selectEntity(input, output);
        if (selected.isEmpty()) {
            return;
        }
        T current = selected.get();
        T draft = updateDraft(current, input, output);
        if (draft == null) {
            return;
        }
        try {
            T updated = service().update(current.getId(), draft);
            output.println("Updated " + entityName() + ": " + describe(updated));
        } catch (AtlasException exception) {
            output.println(exception.getMessage());
        }
    }

    private void delete(InputReader input, OutputWriter output) {
        Optional<T> selected = selectEntity(input, output);
        if (selected.isEmpty()) {
            return;
        }
        T target = selected.get();
        Boolean confirmed = InputPrompts.readYesNo(input, output,
                "Delete \"" + describe(target) + "\"?");
        if (confirmed == null || !confirmed) {
            return;
        }
        try {
            service().delete(target.getId());
            output.println("Deleted " + entityName() + ": " + describe(target));
        } catch (AtlasException exception) {
            output.println(exception.getMessage());
        }
    }

    /**
     * Lists all entities and lets the user pick one by number.
     *
     * @return the chosen entity, or empty when the user cancels
     */
    private Optional<T> selectEntity(InputReader input, OutputWriter output) {
        List<T> all = service().findAll();
        output.println();
        if (all.isEmpty()) {
            output.println("No " + entityName() + "s yet.");
            return Optional.empty();
        }
        for (int index = 0; index < all.size(); index++) {
            output.println("  " + (index + 1) + ". " + describe(all.get(index)));
        }
        while (true) {
            output.print("Select a " + entityName() + " (1-" + all.size() + ") or 0 to cancel > ");
            String value = input.readLine();
            if (value == null) {
                return Optional.empty();
            }
            if ("0".equals(value.trim())) {
                return Optional.empty();
            }
            try {
                int index = Integer.parseInt(value.trim());
                if (index >= 1 && index <= all.size()) {
                    return Optional.of(all.get(index - 1));
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            output.println("Please enter a number between 1 and " + all.size() + ".");
        }
    }

    /**
     * @return the service owning this entity type
     */
    protected abstract EntityService<T> service();

    /**
     * @return the lowercase entity name used in messages, e.g. "subject"
     */
    protected abstract String entityName();

    /**
     * Prompts for all fields of a new entity.
     *
     * @return the draft, or {@code null} when the input stream ended
     */
    protected abstract T createDraft(InputReader input, OutputWriter output);

    /**
     * Prompts for updated fields of an existing entity; blank answers keep
     * the current value.
     *
     * @param current the entity being edited
     * @return the updated entity, or {@code null} when the input stream ended
     */
    protected abstract T updateDraft(T current, InputReader input, OutputWriter output);

    /**
     * Renders one entity as a single line for lists and messages.
     */
    protected abstract String describe(T entity);
}
