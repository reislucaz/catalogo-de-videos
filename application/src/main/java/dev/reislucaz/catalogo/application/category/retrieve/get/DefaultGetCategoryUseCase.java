package dev.reislucaz.catalogo.application.category.retrieve.get;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.category.CategoryID;
import dev.reislucaz.catalogo.domain.exceptions.DomainException;
import dev.reislucaz.catalogo.domain.exceptions.NotFoundException;
import dev.reislucaz.catalogo.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryUseCase extends GetCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public GetCategoryOutput execute(GetCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());

        final var aCategory = this.categoryGateway.findById(anId).orElseThrow(notFound(anId));

        return GetCategoryOutput.from(aCategory);
    }

    private Supplier<DomainException> notFound (final CategoryID anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
