package dev.reislucaz.catalogo.application.category.update;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.category.CategoryID;
import dev.reislucaz.catalogo.domain.exceptions.DomainException;
import dev.reislucaz.catalogo.domain.validation.Error;
import dev.reislucaz.catalogo.domain.validation.handler.NotificationHandler;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<NotificationHandler, UpdateCategoryOutput> execute(UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = NotificationHandler.create();

        aCategory.update(aName, aDescription, isActive).validate(notification);

        return notification.hasError() ? Either.left(notification) : update(aCategory);
    }

    private Either<NotificationHandler, UpdateCategoryOutput> update(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(NotificationHandler::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound (final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with id %s not found".formatted(anId.toString())));
    }
}
