package dev.reislucaz.catalogo.domain.exceptions;

import dev.reislucaz.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException

{
    protected final List<Error> errors;

    protected DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);

        this.errors = anErrors;
    }

    public static DomainException with(final Error anError) {
        return new DomainException(anError.aMessage(), List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
