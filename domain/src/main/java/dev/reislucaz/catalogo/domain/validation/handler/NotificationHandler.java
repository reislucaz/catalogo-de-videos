package dev.reislucaz.catalogo.domain.validation.handler;

import dev.reislucaz.catalogo.domain.exceptions.DomainException;
import dev.reislucaz.catalogo.domain.validation.Error;
import dev.reislucaz.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class NotificationHandler implements ValidationHandler {
    private final List<Error> errors;

    private NotificationHandler(final List<Error> errors) {
        this.errors = errors;
    }

    public static NotificationHandler create() {
        return new NotificationHandler(new ArrayList<>());
    }

    public static NotificationHandler create(final Error error) {
        final var notification = new NotificationHandler(new ArrayList<>());

        notification.append(error);

        return notification;
    }

    @Override
    public ValidationHandler append(final Error anError) {
        this.errors.add(anError);

        return this;
    }

    @Override
    public ValidationHandler append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());

        return this;
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch(final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch(final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
