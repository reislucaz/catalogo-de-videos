package dev.reislucaz.catalogo.domain.category;

import dev.reislucaz.catalogo.domain.validation.Error;
import dev.reislucaz.catalogo.domain.validation.ValidationHandler;
import dev.reislucaz.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;

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

        if (name.length() > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
