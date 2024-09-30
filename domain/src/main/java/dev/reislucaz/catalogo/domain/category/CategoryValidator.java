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
        if (this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }
    }
}
