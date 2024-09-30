package dev.reislucaz.catalogo.domain.category;

import dev.reislucaz.catalogo.domain.validation.Error;
import dev.reislucaz.catalogo.domain.validation.ValidationHandler;
import dev.reislucaz.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    protected CategoryValidator(final Category aCategory, ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();

        if (name.length() > 255 || length < 3) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
