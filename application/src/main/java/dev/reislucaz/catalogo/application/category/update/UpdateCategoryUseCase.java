package dev.reislucaz.catalogo.application.category.update;

import dev.reislucaz.catalogo.application.UseCase;
import dev.reislucaz.catalogo.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<NotificationHandler, UpdateCategoryOutput>> {
}
