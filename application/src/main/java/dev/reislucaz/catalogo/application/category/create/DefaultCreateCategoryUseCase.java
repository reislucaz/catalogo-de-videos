package dev.reislucaz.catalogo.application.category.create;

import dev.reislucaz.catalogo.domain.category.Category;
import dev.reislucaz.catalogo.domain.category.CategoryGateway;
import dev.reislucaz.catalogo.domain.validation.handler.NotificationHandler;
import dev.reislucaz.catalogo.domain.validation.handler.ThrowsValidationHandler;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<NotificationHandler, CreateCategoryOutput> execute(CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = NotificationHandler.create();

        final var aCategory = Category.newCategory(aName, aDescription, isActive);

        aCategory.validate(notification);

        return notification.hasError() ? Either.left(notification) : create(aCategory);
    }

    private Either<NotificationHandler, CreateCategoryOutput> create(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .map(CreateCategoryOutput::from)
                .mapLeft(NotificationHandler::create);
    }
}
