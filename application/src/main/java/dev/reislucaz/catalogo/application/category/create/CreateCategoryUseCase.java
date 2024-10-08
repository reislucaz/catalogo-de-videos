package dev.reislucaz.catalogo.application.category.create;

import dev.reislucaz.catalogo.application.UseCase;
import dev.reislucaz.catalogo.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<NotificationHandler, CreateCategoryOutput>> {
}
